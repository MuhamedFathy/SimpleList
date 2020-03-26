package com.simple.list.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;

public class NetworkUtils {

  public static boolean isNetworkAvailable(Context context) {
    if (context == null) return false;
    ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
        Context.CONNECTIVITY_SERVICE
    );
    if (connectivityManager == null) return false;
    Network network = connectivityManager.getActiveNetwork();
    NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
    if (networkCapabilities == null) return false;
    return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
        || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
  }
}
