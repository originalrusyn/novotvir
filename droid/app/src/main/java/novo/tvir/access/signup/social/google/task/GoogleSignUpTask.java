package novo.tvir.access.signup.social.google.task;

import android.os.Bundle;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.activity.SignUpActivity;
import novo.tvir.access.signup.social.google.fragment.GoogleSignUpFragment;
import novo.tvir.access.signup.social.google.service.SignUpByGoogleService;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.StringRes;
import persist.domain.Account;

// @author Mykhaylo Titov on 30.04.2015.
@Slf4j
@EBean
public class GoogleSignUpTask {

    @RootContext SignUpActivity signUpActivity;
    @FragmentById(R.id.google_sign_up_fragment) GoogleSignUpFragment googleSignUpFragment;
    @StringRes(R.string.g_plus_scope) String scope;
    @Bean SignUpByGoogleService signUpByGoogleService;

    @Background
    public void signUp(String email){
        try {
            String token = GoogleAuthUtil.getToken(signUpActivity, email, scope, new Bundle());
            Account account = signUpByGoogleService.signUp(token);
            onSignUpSuccess(account);
        }catch (UserRecoverableAuthException e){
            handleException(e);
        } catch (Exception e) {
            log.error("Some unrecoverable exception has occurred", e);
            handleSignUpError(e);
        }
    }

    @UiThread
    void handleSignUpError(Exception e){
        googleSignUpFragment.handleSignUpError(e);
    }

    @UiThread
    public void handleException(UserRecoverableAuthException e){
        googleSignUpFragment.handleException(e);
    }

    @UiThread
    void onSignUpSuccess(Account account){
        googleSignUpFragment.onSignUpSuccess(account);
    }

}
