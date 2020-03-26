package com.simple.list.di.presentation.fragment;

import androidx.lifecycle.ViewModel;
import com.simple.list.di.presentation.scopes.PerFragment;
import com.simple.list.di.presentation.viewmodel.ViewModelKey;
import com.simple.list.presentation.ui.fragment.details.DetailsViewModel;
import com.simple.list.presentation.ui.fragment.home.HomeViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class FragmentViewModelModule {

  @PerFragment
  @Binds
  @IntoMap
  @ViewModelKey(HomeViewModel.class)
  abstract ViewModel bindHomeViewModel(HomeViewModel homeViewModel);

  @PerFragment
  @Binds
  @IntoMap
  @ViewModelKey(DetailsViewModel.class)
  abstract ViewModel bindDetailsViewModel(DetailsViewModel detailsViewModel);
}
