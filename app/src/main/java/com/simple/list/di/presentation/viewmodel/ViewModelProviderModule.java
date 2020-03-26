package com.simple.list.di.presentation.viewmodel;

import androidx.lifecycle.ViewModelProvider;
import com.simple.list.di.presentation.scopes.PerFragment;
import dagger.Binds;
import dagger.Module;

@Module
public abstract class ViewModelProviderModule {

  @PerFragment
  @Binds abstract ViewModelProvider.Factory bindViewModelFactory(
      ViewModelFactoryProvider viewModelFactoryProvider
  );
}
