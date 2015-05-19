package novo.tvir.access.signup.task;

import android.os.Bundle;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.activity.SignUpActivity;
import novo.tvir.access.signup.service.SignUpByGoogleService;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.StringRes;

// @author Titov Mykhaylo (titov) on 30.04.2015.
@Slf4j
@EBean
public class GetGoogleAuthTokenTask {

    @RootContext SignUpActivity signUpActivity;
    @StringRes(R.string.g_plus_scope) String scope;
    @Bean SignUpByGoogleService signUpByGoogleService;

    @Background
    public void fetchToken(String email){
        try {
            Bundle extras = new Bundle();
            String token = GoogleAuthUtil.getToken(signUpActivity, email, scope, extras);
            signUpByGoogleService.signup(token);
            onFetchTokenCompleteSuccess(token);
        }catch (UserRecoverableAuthException e){
            handleException(e);
        } catch (Exception e) {
            log.error("Some unrecoverable exception has occurred", e);
            handleSignUpError(e);
        }
    }

    @UiThread
    void handleException(UserRecoverableAuthException e){
        signUpActivity.handleException(e);
    }

    @UiThread
    void onFetchTokenCompleteSuccess(String token){
        signUpActivity.onFetchTokenCompleteSuccess(token);
    }

    @UiThread
    void handleSignUpError(Exception e){
        signUpActivity.handleSignUpError(e);
    }

}
