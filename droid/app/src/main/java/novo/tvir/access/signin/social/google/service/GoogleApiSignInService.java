package novo.tvir.access.signin.social.google.service;

import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import lombok.Delegate;
import novo.tvir.R;
import novo.tvir.access.signin.social.google.fragment.GoogleSignInFragment;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

// @author Titov Mykhaylo (titov) on 27.04.2015.
@EBean
public class GoogleApiSignInService {

    @RootContext FragmentActivity activity;

    @Delegate GoogleApiClient googleApiClient;

    @AfterInject
    public void init(){
        GoogleSignInFragment googleSignInFragment = (GoogleSignInFragment)activity.getSupportFragmentManager().findFragmentById(R.id.google_sign_in_fragment);
        googleApiClient = new GoogleApiClient.Builder(activity.getApplicationContext())
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(googleSignInFragment)
                .addOnConnectionFailedListener(googleSignInFragment)
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
