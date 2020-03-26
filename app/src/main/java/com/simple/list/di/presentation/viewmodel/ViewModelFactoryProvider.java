package com.simple.list.di.presentation.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;

public class ViewModelFactoryProvider implements ViewModelProvider.Factory {

  private final Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelMap;

  @Inject public ViewModelFactoryProvider(
      Map<Class<? extends ViewModel>, Provider<ViewModel>> viewModelMap
  ) {
    this.viewModelMap = viewModelMap;
  }

  @SuppressWarnings({ "unchecked", "ConstantConditions" })
  @NonNull
  @Override public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
    return (T) viewModelMap.get(modelClass).get();
  }
}
