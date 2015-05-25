package novo.tvir.access.signup.email.task;

import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.activity.SignUpActivity;
import novo.tvir.access.signup.email.fragment.EmailSignUpFragment;
import novo.tvir.access.signup.email.service.SignUpByEmailService;
import org.androidannotations.annotations.*;

// @author: m on 29.03.15 17:21.
@EBean
@Slf4j
public class UserSignUpTask {

    @Bean SignUpByEmailService signUpByEmailService;
    @FragmentById(R.id.email_signup_fragment) EmailSignUpFragment emailSignUpFragment;

    @RootContext SignUpActivity signUpActivity;

    @Background(id = "signup")
    public void signup(String mEmail, String mPassword) {
        onLoginComplete(signUpByEmailService.signup(mEmail, mPassword));
    }

    @UiThread
    void onLoginComplete(boolean success) {
        emailSignUpFragment.onLoginComplete(success);
    }

}
