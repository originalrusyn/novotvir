package novo.tvir.access.signin.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import novo.tvir.R;
import novo.tvir.access.signin.email.fragment.EmailSignInFragment;
import novo.tvir.access.signin.social.google.fragment.GoogleSignInFragment;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.IntegerRes;


@SuppressLint("Registered")
@EActivity(R.layout.activity_signin)
public class SignInActivity extends FragmentActivity implements GoogleSignInFragment.SignInProgressListener, EmailSignInFragment.SignInByEmailListener {

    @ViewById(R.id.sign_up_progress) View progressView;

    @ViewById(R.id.sign_in_form) View signInFormView;

    @IntegerRes(android.R.integer.config_shortAnimTime) int shortAnimTime;

    @FragmentById(R.id.email_sign_in_fragment) EmailSignInFragment emailSignInFragment;

    @Override
    public void populateAutoComplete() {
        emailSignInFragment.populateAutoComplete();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void setProgressBarVisible(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {

            signInFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            signInFormView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    signInFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            signInFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onConnectionChanged(boolean connected) {
        emailSignInFragment.onConnectionChanged(connected);
    }
}



