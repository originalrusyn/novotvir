package service;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

// @author Titov Mykhaylo (titov) on 30.04.2015.
@EBean
public class NetworkService {

    @RootContext Context context;

    public boolean isOnline(){
        ConnectivityManager connectivityManagernMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManagernMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }
}
