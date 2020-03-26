package com.simple.list.di.application;

import com.simple.list.data.remote.api.UserApi;
import com.simple.list.di.application.scopes.ApplicationScope;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module(includes = { NetworkModule.class })
public class ApiModule {

  @Provides
  @ApplicationScope UserApi provideUserApi(Retrofit retrofit) {
    return retrofit.create(UserApi.class);
  }
}
