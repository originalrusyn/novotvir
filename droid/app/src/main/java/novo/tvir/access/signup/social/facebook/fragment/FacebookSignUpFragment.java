package novo.tvir.access.signup.social.facebook.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

// @author: Mykhaylo Titov on 01.06.15 19:57.
@Slf4j
@EFragment(R.layout.fragment_facebook_signup)
public class FacebookSignUpFragment extends Fragment{

    @ViewById(R.id.facebook_sign_up_button) LoginButton signUpButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        FacebookSdk.sdkInitialize(activity.getApplicationContext());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //signUpButton.setReadPermissions("user_friends");
        signUpButton.setFragment(this);
    }
}
