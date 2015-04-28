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

    public void revokeAccessAndDisconnect(ResultCallback<Status> resultCallback){
        clearDefaultAccount();
        Plus.AccountApi.revokeAccessAndDisconnect(googleApiClient).setResultCallback(resultCallback);
    }

    public void clearDefaultAccount() {
        Plus.AccountApi.clearDefaultAccount(googleApiClient);
    }
}
