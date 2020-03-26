package com.simple.list.di.application;

import android.app.Application;
import com.simple.list.di.application.scopes.ApplicationScope;
import com.simple.list.di.presentation.fragment.FragmentSubComponent;
import dagger.BindsInstance;
import dagger.Component;

@ApplicationScope
@Component(modules = { ApiModule.class, StorageModule.class })
public interface ApplicationComponent {

  FragmentSubComponent.Builder getFragmentSubComponent();

  @Component.Builder
  interface Builder {

    @BindsInstance Builder bindApplication(Application application);

    ApplicationComponent build();
  }
}
