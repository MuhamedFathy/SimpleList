package com.simple.list.di.application;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.simple.list.BuildConfig;
import com.simple.list.di.application.scopes.ApplicationScope;
import com.simple.list.utilities.NetworkUtils;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.util.concurrent.TimeUnit.SECONDS;

@Module
public class NetworkModule {

  @Provides
  @ApplicationScope Gson provideGson() {
    return new GsonBuilder().setLenient().create();
  }

  @Provides
  @ApplicationScope Retrofit provideRetrofitInstance(OkHttpClient client, Gson gson) {
    return new Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(client)
        .build();
  }

  @Provides
  @ApplicationScope OkHttpClient provideOkHttpClient(
      HttpLoggingInterceptor loggingInterceptor
  ) {
    return new OkHttpClient.Builder()
        .connectTimeout(20, SECONDS)
        .readTimeout(30, SECONDS)
        .writeTimeout(20, SECONDS)
        .retryOnConnectionFailure(true)
        .addInterceptor(loggingInterceptor)
        .build();
  }

  @Provides
  @ApplicationScope HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(BuildConfig.DEBUG ? Level.BODY : Level.NONE);
    return logging;
  }

  @Provides
  @ApplicationScope NetworkUtils provideNetworkUtils(Application application) {
    return new NetworkUtils(application);
  }
}
