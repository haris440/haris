package kido.sparks.app;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Check_Network_Class {
    Context context;
public Check_Network_Class(Context context)
{
    this.context=context;
}

    public  boolean checkNetworkConnection() {
        boolean outcome = false;

        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo[] networkInfos = cm.getAllNetworkInfo();

            for (NetworkInfo tempNetworkInfo : networkInfos) {

                if (tempNetworkInfo.isConnected()) {
                    outcome = true;
                    break;
                }
            }
        }

        return outcome;
    }
}
