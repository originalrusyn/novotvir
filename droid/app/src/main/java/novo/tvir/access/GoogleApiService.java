package novo.tvir.access;

import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import lombok.Delegate;
import novo.tvir.R;
import novo.tvir.access.signup.social.google.fragment.GoogleSignUpFragment;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

// @author Titov Mykhaylo (titov) on 27.04.2015.
@EBean
public class GoogleApiService {

    @RootContext FragmentActivity activity;

    //@FragmentById(R.id.google_signup_fragment) GoogleSignUpFragment googleSignUpFragment;

    @Delegate GoogleApiClient googleApiClient;

    @AfterInject
    public void init(){
        GoogleSignUpFragment googleSignUpFragment = (GoogleSignUpFragment)activity.getSupportFragmentManager().findFragmentById(R.id.google_signup_fragment);
        googleApiClient = new GoogleApiClient.Builder(activity.getApplicationContext())
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(googleSignUpFragment)
                .addOnConnectionFailedListener(googleSignUpFragment)
                .build();
    }

    public void revokeAccessAndDisconnect(final ResultCallback<Status> resultCallback){
        clearDefaultAccount();
        ResultCallback<Status> resultCallbackWrapper = new ResultCallback<Status>() {
            @Override
            public void onResult(Status status) {
                if(status.isSuccess()){
                    googleApiClient.disconnect();
                }
                resultCallback.onResult(status);
            }
        };
        Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient).setResultCallback(resultCallbackWrapper);
    }

    public void clearDefaultAccount() {
        Plus.AccountApi.clearDefaultAccount(googleApiClient);
    }
}
