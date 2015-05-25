package novo.tvir.access.signup.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import lombok.extern.slf4j.Slf4j;
import novo.tvir.R;
import novo.tvir.access.signup.email.fragment.EmailSignUpFragment;
import novo.tvir.access.signup.social.google.fragment.GoogleSignUpFragment;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntegerRes;

@Slf4j
@EActivity(R.layout.activity_signup)
public class SignUpActivity extends FragmentActivity implements GoogleSignUpFragment.SignUpProgressListener, EmailSignUpFragment.A {

    @ViewById(R.id.signup_progress) View progressView;

    @ViewById(R.id.signup_form) View signUpFormView;

    @IntegerRes(android.R.integer.config_shortAnimTime) int shortAnimTime;

    @FragmentById(R.id.email_signup_fragment) EmailSignUpFragment emailSignUpFragment;

    @Override
    public void populateAutoComplete() {
        emailSignUpFragment.populateAutoComplete();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void setProgressBarVisible(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            signUpFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            progressView.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onConnectionChanged(boolean connected) {
        emailSignUpFragment.onConnectionChanged(connected);
    }
}
