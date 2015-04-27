package novo.tvir.access.signin.task;

import lombok.extern.slf4j.Slf4j;
import novo.tvir.access.signup.activity.SignUpActivity;
import novo.tvir.access.signup.service.SignUpService;
import org.androidannotations.annotations.*;

// @author: m on 29.03.15 17:21.
@EBean
@Slf4j
public class UserSignInTask {

    @Bean SignUpService signUpService;

    @RootContext SignUpActivity signUpActivity;

    @Background(id = "signin")
    public void signin(String mEmail, String mPassword) {
        onLoginComplete(signUpService.signup(mEmail, mPassword));
    }

    @UiThread
    void onLoginComplete(boolean success) {
        signUpActivity.onLoginComplete(success);
    }

}
