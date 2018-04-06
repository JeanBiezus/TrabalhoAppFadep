package fadep.android.pos.trabalhoapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by jean on 05/04/18.
 */

public class InternetConnectedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

        boolean connected = (activeNetInfo != null) && (activeNetInfo.isConnected());

        Log.e("NETWORK", "LOGGER: Network status change, connected = " + String.valueOf(connected) );

    }
}