package novo.tvir.access.signup.email.task;

import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.email.fragment.EmailSignUpFragment;
import novo.tvir.access.signup.email.service.SignUpByEmailService;
import org.androidannotations.annotations.*;

// @author: Mykhaylo Titov on 29.03.15 17:21.
@EBean
@Slf4j
public class UserSignUpTask {

    @Bean SignUpByEmailService signUpByEmailService;
    @FragmentById(R.id.email_sign_up_fragment) EmailSignUpFragment emailSignUpFragment;

    @Background(id = "signUp")
    public void signUp(String mEmail, String mPassword) {
        onLoginComplete(signUpByEmailService.signUp(mEmail, mPassword));
    }

    @UiThread
    void onLoginComplete(boolean success) {
        emailSignUpFragment.onSignUpComplete(success);
    }

}
