package novo.tvir.access.signin.task;

import lombok.extern.slf4j.Slf4j;
import novo.tvir.access.signin.activity.SignInActivity;
import novo.tvir.access.signin.service.SignInService;
import org.androidannotations.annotations.*;

// @author Titov Mykhaylo (titov) on 27.04.2015.
@EBean
@Slf4j
public class UserSignInTask {

    @Bean
    SignInService signInService;

    @RootContext
    SignInActivity signInActivity;

    @Background(id = "signin")
    public void signin(String mEmail, String mPassword) {
        onLoginComplete(signInService.signin(mEmail, mPassword));
    }

    @UiThread
    void onLoginComplete(boolean success) {
        signInActivity.onLoginComplete(success);
    }

}
