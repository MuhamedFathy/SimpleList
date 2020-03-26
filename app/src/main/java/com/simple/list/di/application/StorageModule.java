package com.simple.list.di.application;

import android.app.Application;
import com.simple.list.data.local.SimpleDataBase;
import com.simple.list.data.local.UserDao;
import com.simple.list.di.application.scopes.ApplicationScope;
import dagger.Module;
import dagger.Provides;

@Module
public class StorageModule {

  @Provides
  @ApplicationScope UserDao provideUserDao(Application application) {
    return SimpleDataBase.get(application).getUserDao();
  }
}
