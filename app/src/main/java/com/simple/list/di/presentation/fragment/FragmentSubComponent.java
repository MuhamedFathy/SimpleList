package com.simple.list.di.presentation.fragment;

import androidx.fragment.app.Fragment;
import com.simple.list.di.presentation.scopes.PerFragment;
import com.simple.list.presentation.ui.fragment.details.DetailsFragment;
import com.simple.list.presentation.ui.fragment.home.HomeFragment;
import dagger.BindsInstance;
import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = { FragmentViewModelModule.class })
public interface FragmentSubComponent {

  void bind(HomeFragment homeFragment);

  void bind(DetailsFragment detailsFragment);

  @Subcomponent.Builder
  interface Builder {

    @BindsInstance Builder bindsFragmentContext(Fragment fragment);

    FragmentSubComponent build();
  }
}
