package novo.tvir.access.signin.social.google.task;

import android.os.Bundle;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signin.activity.SignInActivity;
import novo.tvir.access.signin.social.google.fragment.GoogleSignInFragment;
import novo.tvir.access.signin.social.google.service.SignInByGoogleService;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.StringRes;

// @author Mykhaylo Titov on 30.04.2015.
@Slf4j
@EBean
public class GoogleSignInTask {

    @RootContext SignInActivity signInActivity;
    @FragmentById(R.id.google_sign_in_fragment) GoogleSignInFragment googleSignInFragment;
    @StringRes(R.string.g_plus_scope) String scope;
    @Bean SignInByGoogleService signInByGoogleService;

    @Background
    public void signIn(String email){
        try {
            String token = GoogleAuthUtil.getToken(signInActivity, email, scope, new Bundle());
            signInByGoogleService.signIn(token);
            onFetchTokenComplete(token);
        }catch (UserRecoverableAuthException e){
            handleException(e);
        } catch (Exception e) {
            log.error("Some unrecoverable exception has occurred", e);
            handleSignUpError(e);
        }
    }

    @UiThread
    void handleSignUpError(Exception e){
        googleSignInFragment.handleSignUpError(e);
    }

    @UiThread
    public void handleException(UserRecoverableAuthException e){
        googleSignInFragment.handleException(e);
    }

    @UiThread
    void onFetchTokenComplete(String token){
        log.info(token);
    }

}
