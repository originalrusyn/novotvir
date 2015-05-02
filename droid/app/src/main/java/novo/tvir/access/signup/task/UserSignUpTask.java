package novo.tvir.access.signup.task;

import lombok.extern.slf4j.Slf4j;
import novo.tvir.access.signup.activity.SignUpActivity;
import novo.tvir.access.signup.service.SignUpByEmailService;
import org.androidannotations.annotations.*;

// @author: m on 29.03.15 17:21.
@EBean
@Slf4j
public class UserSignUpTask {

    @Bean SignUpByEmailService signUpByEmailService;

    @RootContext SignUpActivity signUpActivity;

    @Background(id = "signup")
    public void signup(String mEmail, String mPassword) {
        onLoginComplete(signUpByEmailService.signup(mEmail, mPassword));
    }

    @UiThread
    void onLoginComplete(boolean success) {
        signUpActivity.onLoginComplete(success);
    }

}
