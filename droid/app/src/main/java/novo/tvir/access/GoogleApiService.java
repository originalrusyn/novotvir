package novo.tvir.access;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import lombok.Delegate;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

// @author Titov Mykhaylo (titov) on 27.04.2015.
@EBean
public class GoogleApiService {

    @RootContext
    GoogleApiActivity googleApiActivity;

    @Delegate
    GoogleApiClient googleApiClient;

    @AfterInject
    public void init(){
        googleApiClient = new GoogleApiClient.Builder(googleApiActivity.getApplicationContext())
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .addConnectionCallbacks(googleApiActivity)
                .addOnConnectionFailedListener(googleApiActivity)
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
