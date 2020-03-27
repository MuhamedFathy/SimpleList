package com.simple.list.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import javax.inject.Inject;

public class NetworkUtils {

  private Context context;

  @Inject public NetworkUtils(Context context) {
    this.context = context;
  }

  public boolean isNetworkAvailable() {
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
