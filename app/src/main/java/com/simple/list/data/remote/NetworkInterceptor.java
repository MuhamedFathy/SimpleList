package com.simple.list.data.remote;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public abstract class NetworkInterceptor implements Interceptor {

  public static final String NETWORK_ISSUE = "Network is not available";

  public abstract boolean isInternetAvailable();

  @NotNull @Override public Response intercept(@NotNull Chain chain) throws IOException {
    Request request = chain.request();
    if (!isInternetAvailable()) throw new IllegalStateException(NETWORK_ISSUE);
    return chain.proceed(request);
  }
}
