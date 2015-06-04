package novo.tvir.access.signup.social.facebook.task;

import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.activity.SignUpActivity;
import novo.tvir.access.signup.social.facebook.fragment.FacebookSignUpFragment;
import novo.tvir.access.signup.social.facebook.service.SignUpByFacebookService;
import org.androidannotations.annotations.*;

// @author: Mykhaylo Titov on 03.06.15 23:35.
@Slf4j
@EBean
public class FacebookSignUpTask {

    @RootContext SignUpActivity signUpActivity;
    @FragmentById(R.id.email_signup_fragment) FacebookSignUpFragment facebookSignUpFragment;
    @Bean SignUpByFacebookService signUpByFacebookService;

    @Background
    public void signup(String token){
        try {
            signUpByFacebookService.signup(token);
        } catch (Exception e) {
            log.error("Some unrecoverable exception has occurred", e);
        }
    }
}
