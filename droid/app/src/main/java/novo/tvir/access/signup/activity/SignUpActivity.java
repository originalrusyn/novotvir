package novo.tvir.access.signup.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.*;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.GoogleApiActivity;
import novo.tvir.access.GoogleApiService;
import novo.tvir.access.PasswordFormatValidator;
import novo.tvir.access.signup.task.UserSignUpTask;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.IntegerRes;
import util.EmailFormatValidator;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@EActivity(R.layout.activity_signup)
public class SignUpActivity extends GoogleApiActivity implements LoaderCallbacks<Cursor> {

    @NonConfigurationInstance @Bean UserSignUpTask userSignUpTask;

    @ViewById(R.id.email) AutoCompleteTextView emailView;
    @ViewById(R.id.password) EditText passwordView;
    @ViewById(R.id.signup_progress) View progressView;
    @ViewById(R.id.email_signup_form) View emailSignUpFormView;
    @ViewById(R.id.plus_sign_up_button) SignInButton pusSignInButton;
    @ViewById(R.id.plus_sign_out_buttons) View signOutButtons;
    @ViewById(R.id.signup_form) View signUpFormView;
    @IntegerRes(android.R.integer.config_shortAnimTime) int shortAnimTime;

    @Bean EmailFormatValidator emailFormatValidator;
    @Bean PasswordFormatValidator passwordFormatValidator;
    @Bean GoogleApiService googleApiService;

    private static final int REQUEST_RESOLVE_ERROR = 49404;

    // A flag to stop multiple dialogues appearing for the user
    private boolean autoResolveOnFail;

    public boolean googleApiClientIsConnecting = false;

    // The saved result from {@link #onConnectionFailed(ConnectionResult)}.  If a connection
    // attempt has been made, this is non-null.
    // If this IS null, then the connect method is still running.
    private ConnectionResult connectionResult;

    @AfterViews
    void onAfterViews() {
        if (!supportsGooglePlayServices()) {
            pusSignInButton.setVisibility(View.GONE);
            return;
        }
        populateAutoComplete();
    }

    @EditorAction(R.id.password)
    public boolean onPasswordEditorAction(int id) {
        if (id == R.id.signin || id == EditorInfo.IME_NULL) {
            attemptSignUp();
            return true;
        }
        return false;
    }


    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Click(R.id.email_sign_up_button)
    public void attemptSignUp() {
        emailView.setError(null);
        passwordView.setError(null);

        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !passwordFormatValidator.isValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            focusView = emailView;
            cancel = true;
        } else if (!emailFormatValidator.isValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            focusView = emailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            userSignUpTask.signup(email, password);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            signUpFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Click(R.id.plus_sign_up_button)
    public void signUp() {
        if (!googleApiService.isConnected()) {
            setProgressBarVisible(true);
            // Make sure that we will start the resolution (e.g. fire the intent and pop up a
            // dialog for the user) for any errors that come in.
            autoResolveOnFail = true;
            if (connectionResult != null) {
                startResolution();
            } else {
                initiatePlusClientConnect();
            }
        }

        updateConnectButtonState();
    }

    private void initiatePlusClientConnect() {
        if (!googleApiService.isConnected() && !googleApiService.isConnecting()) {
            googleApiService.connect();
        }
    }

    private void initiatePlusClientDisconnect() {
        googleApiService.disconnect();
    }

    @Click(R.id.plus_sign_out_button)
    public void signOut() {
        initiatePlusClientDisconnect();
        updateConnectButtonState();
    }


    @Click(R.id.plus_disconnect_button)
    public void revokeAccess() {
        initiatePlusClientDisconnect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initiatePlusClientConnect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        initiatePlusClientDisconnect();
    }

    private void setProgressBarVisible(boolean flag) {
        googleApiClientIsConnecting = flag;
        showProgress(flag);
    }

    /**
     * A helper method to flip the mResolveOnFail flag and start the resolution
     * of the ConnectionResult from the failed connect() call.
     */
    private void startResolution() {
        try {
            // Don't start another resolution now until we have a result from the activity we're
            // about to start.
            autoResolveOnFail = false;
            // If we can resolve the error, then call start resolution and pass it an integer tag
            // we can use to track.
            // This means that when we get the onActivityResult callback we'll know it's from
            // being started here.
            connectionResult.startResolutionForResult(this, REQUEST_RESOLVE_ERROR);
        } catch (IntentSender.SendIntentException e) {
            connectionResult = null;
            initiatePlusClientConnect();
        }
    }

    /**
     * An earlier connection failed, and we're now receiving the result of the resolution attempt
     * by PlusClient.
     *
     * @see #onConnectionFailed(ConnectionResult)
     */
    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        updateConnectButtonState();
        if (requestCode == REQUEST_RESOLVE_ERROR && responseCode == RESULT_OK) {
            // If we have a successful result, we will want to be able to resolve any further
            // errors, so turn on resolution with our flag.
            autoResolveOnFail = true;
            // If we have a successful result, let's call connect() again. If there are any more
            // errors to resolve we'll get our onConnectionFailed, but if not,
            // we'll get onConnected.
            initiatePlusClientConnect();
        } else if (requestCode == REQUEST_RESOLVE_ERROR) {
            // If we've got an error we can't resolve, we're no longer in the midst of signing
            // in, so we can stop the progress spinner.
            setProgressBarVisible(false);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        updateConnectButtonState();
        setProgressBarVisible(false);
        onPlusClientSignIn();
    }

    @Override
    public void onConnectionSuspended(int cause) {
        updateConnectButtonState();
        onPlusClientSignOut();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        updateConnectButtonState();

        connectionResult = result;
        if (result.hasResolution()) {
            if (autoResolveOnFail) {
                // This is a local helper function that starts the resolution of the problem,
                // which may be showing the user an account chooser or similar.
                startResolution();
            }
        } else {
            showErrorDialog(result.getErrorCode());
            autoResolveOnFail = true;
        }
    }

    private static final String DIALOG_ERROR = "dialog_error";

    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        autoResolveOnFail = false;
        updateConnectButtonState();
        setProgressBarVisible(false);
    }

    /* A fragment to display an error dialog */
    public static class ErrorDialogFragment extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Get the error code and retrieve the appropriate dialog
            int errorCode = this.getArguments().getInt(DIALOG_ERROR);
            return GooglePlayServicesUtil.getErrorDialog(errorCode,
                    this.getActivity(), REQUEST_RESOLVE_ERROR);
        }

        @Override
        public void onDismiss(DialogInterface dialog) {
            ((SignUpActivity) getActivity()).onDialogDismissed();
        }
    }


    /**
     * Called when the PlusClient is successfully connected.
     */
    protected void onPlusClientSignIn() {
        log.info("onPlusClientSignIn");
    }

    /**
     * Called when there is a change in connection state.  If you have "Sign in"/ "Connect",
     * "Sign out"/ "Disconnect", or "Revoke access" buttons, this lets you know when their states
     * need to be updated.
     */
    protected void updateConnectButtonState() {
        //TODO: Update this logic to also handle the user logged in by email.
        boolean connected = googleApiService.isConnected();

        signOutButtons.setVisibility(connected ? View.VISIBLE : View.GONE);
        pusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
        emailSignUpFormView.setVisibility(connected ? View.GONE : View.VISIBLE);
    }

    /**
     * Called when the {@link PlusClient} revokes access to this app.
     */
    protected void onPlusClientRevokeAccess() {
        // TODO: Access to the user's G+ account has been revoked.  Per the developer terms, delete
        // any stored user data here.
        log.info("onPlusClientRevokeAccess");
    }

    /**
     * Called when the {@link PlusClient} is disconnected.
     */
    protected void onPlusClientSignOut() {
        log.info("onPlusClientSignOut");

    }

    private boolean supportsGooglePlayServices() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == ConnectionResult.SUCCESS;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Uri uri = Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI, ContactsContract.Contacts.Data.CONTENT_DIRECTORY);
        String selection = ContactsContract.Contacts.Data.MIMETYPE + " = ?";
        String[] selectionArgs = {ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE}; // Select only email addresses.
        String sortOrder = ContactsContract.Contacts.Data.IS_PRIMARY + " DESC"; // Show primary email addresses first. Note that there won't be a primary email address if the user hasn't specified one.

        // Retrieve data rows for the device user's 'profile' contact.
        return new CursorLoader(this, uri, ProfileQuery.PROJECTION, selection, selectionArgs, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }


    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        emailView.setAdapter(new ArrayAdapter<>(SignUpActivity.this, android.R.layout.simple_dropdown_item_1line, emailAddressCollection));
    }

    public void onLoginComplete(boolean success) {
        showProgress(false);

        if (success) {
            finish();
        } else {
            passwordView.setError(getString(R.string.error_incorrect_password));
            passwordView.requestFocus();
        }
    }
}



