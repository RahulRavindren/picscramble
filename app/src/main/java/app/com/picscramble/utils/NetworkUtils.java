package app.com.picscramble.utils;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class NetworkUtils {

    public static boolean haveNetworkConnection(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

            if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) return true;

            NetworkInfo[] netinfo = cm.getAllNetworkInfo();
            for (NetworkInfo info : netinfo) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI && info.isConnected())
                    return true;

                if (info.getType() == ConnectivityManager.TYPE_MOBILE && info.isConnected())
                    return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
