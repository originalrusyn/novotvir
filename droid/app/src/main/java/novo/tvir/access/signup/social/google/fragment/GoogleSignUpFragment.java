package novo.tvir.access.signup.social.google.fragment;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.auth.GooglePlayServicesAvailabilityException;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.GoogleApiService;
import novo.tvir.access.signup.social.google.task.GetGoogleAuthTokenTask;
import org.androidannotations.annotations.*;
import service.NetworkService;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

// @author: Mykhaylo Titov on 23.05.15 11:42.
@Slf4j
@EFragment(R.layout.fragment_google_signup)
public class GoogleSignUpFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final int REQUEST_RESOLVE_ERROR = 49404;

    static final int REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR = 1002;
    public static final int REQUEST_CODE_PICK_ACCOUNT = 1000;

    //@NonConfigurationInstance
    @Bean GetGoogleAuthTokenTask googleAuthTokenTask;
    @Bean GoogleApiService googleApiService;
    @Bean NetworkService networkService;

    @ViewById(R.id.plus_sign_up_button) SignInButton plusSignInButton;
    @ViewById(R.id.plus_sign_out_buttons) View signOutButtons;
    @ViewById(R.id.signup_form) View signUpFormView;

    // A flag to stop multiple dialogues appearing for the user
    @InstanceState boolean autoResolveOnFail;

    // The saved result from {@link #onConnectionFailed(ConnectionResult)}.  If a connection
    // attempt has been made, this is non-null.
    // If this IS null, then the connect method is still running.
    @InstanceState ConnectionResult connectionResult;

    String email;


    SignUpProgressListener signUpProgressListener;

    public interface SignUpProgressListener{
        void populateAutoComplete();
        void setProgressBarVisible(boolean visible);
        void onConnectionChanged(boolean connected);
        FragmentManager getSupportFragmentManager();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try{
            signUpProgressListener = (SignUpProgressListener) activity;
        }catch (Exception e){
            log.error("{} must implement {}", activity, SignUpProgressListener.class, e);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!supportsGooglePlayServices()) {
            plusSignInButton.setVisibility(View.GONE);
            return;
        }
        if(googleApiService.isConnected()) {
            updateConnectButtonState();
        } else {
            signUpProgressListener.populateAutoComplete();
        }
    }

    private boolean supportsGooglePlayServices() {
        return GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity()) == ConnectionResult.SUCCESS;
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
        plusSignInButton.setVisibility(connected ? View.GONE : View.VISIBLE);
        signUpProgressListener.onConnectionChanged(connected);
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
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_LONG).show();
                        updateConnectButtonState();
                    }
                }
            });
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

    @OnActivityResult(REQUEST_CODE_PICK_ACCOUNT)
    void onRequestPickAccount(int responseCode, Intent data){
        if (responseCode == RESULT_OK) {
            if(networkService.isOnline()) {
                email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                googleAuthTokenTask.fetchToken(email);
            }else{
                Toast.makeText(getActivity(), "No network", Toast.LENGTH_SHORT).show();
            }
        } else if (responseCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
        }
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
            connectionResult.startResolutionForResult(getActivity(), REQUEST_RESOLVE_ERROR);
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
            signUpProgressListener.setProgressBarVisible(false);
        }
    }

    public void handleException(UserRecoverableAuthException e){
        if (e instanceof GooglePlayServicesAvailabilityException) {
            log.info("The Google Play services APK is old, disabled, or not present. Show a dialog created by Google Play services that allows the user to update the APK");
            int statusCode = ((GooglePlayServicesAvailabilityException)e).getConnectionStatusCode();
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(statusCode, getActivity(), REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
            dialog.show();
        } else {
            log.info("Unable to authenticate, such as when the user has not yet granted the app access to the account, but the user can fix this.");
            startActivityForResult(e.getIntent(), REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR);
        }
    }

    @OnActivityResult(REQUEST_CODE_RECOVER_FROM_PLAY_SERVICES_ERROR)
    void onRequestRecoverFromPlayServicesError(int responseCode, Intent data){
        if (responseCode == RESULT_OK) {
            if(networkService.isOnline()) {
                email = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                googleAuthTokenTask.fetchToken(email);
            }else{
                Toast.makeText(getActivity(), "No network", Toast.LENGTH_SHORT).show();
            }
        } else if (responseCode == RESULT_CANCELED) {
            Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        initiatePlusClientConnect();
    }

    @Override
    public void onStop() {
        super.onStop();
        initiatePlusClientDisconnect();
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        updateConnectButtonState();
        signUpProgressListener.setProgressBarVisible(false);
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
        dialogFragment.setTargetFragment(this, 0);
        dialogFragment.show(signUpProgressListener.getSupportFragmentManager(), "errordialog");
    }

    /* Called from ErrorDialogFragment when the dialog is dismissed. */
    public void onDialogDismissed() {
        autoResolveOnFail = false;
        updateConnectButtonState();
        signUpProgressListener.setProgressBarVisible(false);
    }
}
