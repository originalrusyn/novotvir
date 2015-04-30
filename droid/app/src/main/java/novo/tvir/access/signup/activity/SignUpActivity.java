package novo.tvir.access.signup.activity;

import android.accounts.AccountManager;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.IntentSender;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.GoogleApiActivity;
import novo.tvir.access.GoogleApiService;
import novo.tvir.access.PasswordFormatValidator;
import novo.tvir.access.signup.fragment.ErrorDialogFragment;
import novo.tvir.access.signup.task.GetGoogleAuthTokenTask;
import novo.tvir.access.signup.task.UserSignUpTask;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.IntegerRes;
import service.NetworkService;
import util.EmailFormatValidator;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@EActivity(R.layout.activity_signup)
public class SignUpActivity extends GoogleApiActivity implements LoaderCallbacks<Cursor> {

    public static final int REQUEST_RESOLVE_ERROR = 49404;
    public static final int REQUEST_CODE_PICK_ACCOUNT = 1000;
    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;

    @NonConfigurationInstance @Bean UserSignUpTask userSignUpTask;
    @NonConfigurationInstance @Bean GetGoogleAuthTokenTask googleAuthTokenTask;

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
    @Bean NetworkService networkService;

    String email;

    // A flag to stop multiple dialogues appearing for the user
    @InstanceState
    boolean autoResolveOnFail;

    // The saved result from {@link #onConnectionFailed(ConnectionResult)}.  If a connection
    // attempt has been made, this is non-null.
    // If this IS null, then the connect method is still running.
    @InstanceState
    ConnectionResult connectionResult;

    @AfterViews
    void onAfterViews() {
        if (!supportsGooglePlayServices()) {
            pusSignInButton.setVisibility(View.GONE);
            return;
        }
        if(googleApiService.isConnected()) {
            updateConnectButtonState();
        } else {
            populateAutoComplete();
        }
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
            setProgressBarVisible(true);
            userSignUpTask.signup(email, password);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void setProgressBarVisible(final boolean show) {
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
        Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, REQUEST_CODE_PICK_ACCOUNT);
//        if (!googleApiService.isConnected()) {
//            setProgressBarVisible(true);
//            // Make sure that we will start the resolution (e.g. fire the intent and pop up a
//            // dialog for the user) for any errors that come in.
//            autoResolveOnFail = true;
//            if (connectionResult != null) {
//                startResolution();
//            } else {
//                initiatePlusClientConnect();
//            }
//        }
//
//        updateConnectButtonState();
    }

    private void initiatePlusClientConnect() {
        if (!googleApiService.isConnected() && !googleApiService.isConnecting()) {
            googleApiService.connect();
        }
    }

    private void initiatePlusClientDisconnect() {
        if(googleApiService.isConnected()) {
            googleApiService.disconnect();
        }
    }

    @Click(R.id.plus_sign_out_button)
    public void signOut() {
        googleApiService.clearDefaultAccount();
        initiatePlusClientDisconnect();
        updateConnectButtonState();
    }


    @Click(R.id.plus_disconnect_button)
    public void revokeAccess() {
        if(googleApiService.isConnected()) {
            googleApiService.revokeAccessAndDisconnect(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                        updateConnectButtonState();
                    }
                }
            });
        }
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
    @OnActivityResult(REQUEST_RESOLVE_ERROR)
    void onRequestResolveErrorResult(int responseCode) {
        updateConnectButtonState();
        if (responseCode == RESULT_OK) {
            // If we have a successful result, we will want to be able to resolve any further
            // errors, so turn on resolution with our flag.
            autoResolveOnFail = true;
            // If we have a successful result, let's call connect() again. If there are any more
            // errors to resolve we'll get our onConnectionFailed, but if not,
            // we'll get onConnected.
            initiatePlusClientConnect();
        } else {
            // If we've got an error we can't resolve, we're no longer in the midst of signing
            // in, so we can stop the progress spinner.
            setProgressBarVisible(false);
        }
    }

    @OnActivityResult(REQUEST_CODE_PICK_ACCOUNT)
    void onRequestPickAccount(int responseCode, Intent data){
        if (responseCode == RESULT_OK) {
            if(networkService.isOnline()) {
                email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                googleAuthTokenTask.fetchToken(email);
            }else{
                Toast.makeText(this, "No network", Toast.LENGTH_SHORT).show();
            }
        } else if (responseCode == RESULT_CANCELED) {
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    public void handleException(UserRecoverableAuthException e){
        if (e instanceof GooglePlayServicesAvailabilityException) {
            log.info("The Google Play services APK is old, disabled, or not present. Show a dialog created by Google Play services that allows the user to update the APK");
            int statusCode = ((GooglePlayServicesAvailabilityException)e).getConnectionStatusCode();
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode, this, REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
            dialog.show();
        } else {
            log.info("Unable to authenticate, such as when the user has not yet granted the app access to the account, but the user can fix this.");
            startActivityForResult(e.getIntent(), REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
        }
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        updateConnectButtonState();
        setProgressBarVisible(false);
    }

    @Override
    public void onConnectionSuspended(int cause) {
        updateConnectButtonState();
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

    private void showErrorDialog(int errorCode) {
        // Create a fragment for the error dialog
        ErrorDialogFragment dialogFragment = new ErrorDialogFragment();
        // Pass the error that should be displayed
        Bundle args = new Bundle();
        args.putInt(ErrorDialogFragment.DIALOG_ERROR, errorCode);
        dialogFragment.setArguments(args);
        dialogFragment.show(getSupportFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        autoResolveOnFail = false;
        updateConnectButtonState();
        setProgressBarVisible(false);
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
        setProgressBarVisible(false);

        if (success) {
            finish();
        } else {
            passwordView.setError(getString(R.string.error_incorrect_password));
            passwordView.requestFocus();
        }
    }
}
