package novo.tvir.access;

import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.GoogleApiClient;

// @author Titov Mykhaylo (titov) on 27.04.2015.
public abstract class GoogleApiActivity extends FragmentActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
}
