package novo.tvir.access.signin.email.task;

import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signin.email.fragment.EmailSignInFragment;
import novo.tvir.access.signin.email.service.SignInByEmailService;
import org.androidannotations.annotations.*;

// @author: Mykhaylo Titov on 29.03.15 17:21.
@EBean
@Slf4j
public class UserSignInTask {

    @Bean SignInByEmailService signInByEmailService;
    @FragmentById(R.id.email_sign_in_fragment) EmailSignInFragment emailSignInFragment;

    @Background(id = "signIn")
    public void signIn(String mEmail, String mPassword) {
        onSignUpComplete(signInByEmailService.signIn(mEmail, mPassword));
    }

    @UiThread
    void onSignUpComplete(boolean success) {
        emailSignInFragment.onSignInComplete(success);
    }

}
