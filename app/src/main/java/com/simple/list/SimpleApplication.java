package com.simple.list;

import android.app.Application;
import androidx.fragment.app.FragmentActivity;
import com.simple.list.di.application.ApplicationComponent;
import com.simple.list.di.application.DaggerApplicationComponent;
import io.reactivex.internal.functions.Functions;
import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

public class SimpleApplication extends Application {

  private ApplicationComponent applicationComponent;

  @Override public void onCreate() {
    super.onCreate();
    RxJavaPlugins.setErrorHandler(Functions.emptyConsumer());
    setupInjection();
    setupTimber();
  }

  private void setupInjection() {
    applicationComponent = DaggerApplicationComponent.builder()
        .bindApplication(this)
        .build();
  }

  private void setupTimber() {
    if (BuildConfig.DEBUG) Timber.plant(new Timber.DebugTree());
  }

  public static SimpleApplication get(FragmentActivity activity) {
    return (SimpleApplication) activity.getApplication();
  }

  public ApplicationComponent getApplicationComponent() {
    return applicationComponent;
  }
}
