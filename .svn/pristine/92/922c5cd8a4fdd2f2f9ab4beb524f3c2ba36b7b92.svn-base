package com.nebulacompanies.ibo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.util.Log;

/**
 * Created by Palak Mehta on 8/9/2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    ConnectionDetector cd;
    static public Boolean isInternetAvailable = false;
    @Override
    public void onReceive(final Context context, final Intent intent) {
        System.out.println("NetworkChangeReceiver onReceive");
        Log.d("Network==","onReceive");
        cd = new ConnectionDetector(context.getApplicationContext());
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.isNetworkAvailable(getApplicationContext()) = isNetworkAvailable(context);//cd.isConnectingToInternet();
                Log.d("Network==","change--"+Utils.isNetworkAvailable(getApplicationContext()));
            }
        }, 5000);*/

        /*Toast toast1 = Toast.makeText(context, "NetworkChangeReceiver : onReceive : " + Utils.isNetworkAvailable(getApplicationContext()) , Toast.LENGTH_SHORT);
        toast1.show();*/
    }
    private boolean isNetworkAvailable( Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        NetworkInfo WiFiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(mobileInfo != null &  mobileInfo.isConnectedOrConnecting() || WiFiInfo != null  & WiFiInfo.isConnectedOrConnecting())
        {
            //do your task here
            return true;
        }
        else {
            //show an alert dialog or something
            return false;
        }
        //return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
