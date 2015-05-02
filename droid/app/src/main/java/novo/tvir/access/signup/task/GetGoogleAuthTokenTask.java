package novo.tvir.access.signup.task;

import android.os.Bundle;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.activity.SignUpActivity;
import novo.tvir.access.signup.service.SignUpByGoogleService;
import org.androidannotations.annotations.*;
import org.androidannotations.annotations.res.StringRes;

import java.io.IOException;

// @author myti on 30.04.2015.
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
            //extras.putString(GoogleAuthUtil.KEY_REQUEST_VISIBLE_ACTIVITIES, "");
            String token = GoogleAuthUtil.getToken(signUpActivity, email, scope, extras);
            signUpByGoogleService.signup(token);
            onFetchTokenComplete(token);
        } catch (IOException e) {
            log.error("Some unrecoverable exception has occurred", e);
        }catch (UserRecoverableAuthException e){
            handleException(e);
        } catch (GoogleAuthException e) {
            log.error("Some unrecoverable exception has occurred", e);
        }
    }

    @UiThread
    public void handleException(UserRecoverableAuthException e){
        signUpActivity.handleException(e);
    }

    @UiThread
    void onFetchTokenComplete(String token){
        log.info(token);
    }

}
