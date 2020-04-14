package com.migueltzabtah.micapilla.Retrofit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by migueltzabtah on 17/08/18.
 */

public class Config {
    //URL Local
    //private static String URL_BASE = "http://10.0.2.2/micapilla/";
    //URL Remoto
    private static String URL_BASE = "http://micapilla.lighthousecode.com/";

    public static String getURLBASE(){
        return URL_BASE;
    }

    //Funcion para verificar la conexión por WI-FI
    public static boolean isConnectedWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }
}
