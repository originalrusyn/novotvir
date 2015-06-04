package novo.tvir.access.signup.social.facebook.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.social.facebook.task.FacebookSignUpTask;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

// @author: Mykhaylo Titov on 01.06.15 19:57.
@Slf4j
@EFragment(R.layout.fragment_facebook_signup)
public class FacebookSignUpFragment extends Fragment{

    @ViewById(R.id.facebook_sign_up_button) LoginButton signUpButton;
    @Bean FacebookSignUpTask facebookSignUpTask;

    CallbackManager callbackManager;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
        log.info(FacebookSdk.getApplicationSignature(activity.getApplicationContext()));
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //signUpButton.setReadPermissions("user_friends");
        signUpButton.setFragment(this);
        signUpButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                log.info(loginResult.getAccessToken().toString());
                facebookSignUpTask.signup(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException e) {
                log.error("Can't login", e);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
