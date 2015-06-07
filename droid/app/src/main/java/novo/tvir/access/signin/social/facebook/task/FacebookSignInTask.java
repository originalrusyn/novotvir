package novo.tvir.access.signin.social.facebook.task;

import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signin.activity.SignInActivity;
import novo.tvir.access.signin.social.facebook.fragment.FacebookSignInFragment;
import novo.tvir.access.signin.social.facebook.service.SignInByFacebookService;
import org.androidannotations.annotations.*;

// @author: Mykhaylo Titov on 03.06.15 23:35.
@Slf4j
@EBean
public class FacebookSignInTask {

    @RootContext SignInActivity signInActivity;
    @FragmentById(R.id.email_sign_in_fragment) FacebookSignInFragment facebookSignInFragment;
    @Bean SignInByFacebookService signInByFacebookService;

    @Background
    public void signIn(String accessToken){
        try {
            signInByFacebookService.signIn(accessToken);
        } catch (Exception e) {
            log.error("Some unrecoverable exception has occurred", e);
        }
    }
}
