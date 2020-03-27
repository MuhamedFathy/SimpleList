package com.simple.list.presentation.ui.fragment.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.simple.list.R;
import com.simple.list.SimpleApplication;
import com.simple.list.di.presentation.fragment.FragmentSubComponent;
import com.simple.list.di.presentation.viewmodel.ViewModelFactoryProvider;
import com.simple.list.presentation.ui.activity.MainActivity;
import com.simple.list.presentation.ui.fragment.home.adapter.UsersAdapter;
import com.simple.list.utilities.Constants;
import io.reactivex.disposables.Disposable;
import javax.inject.Inject;

public class HomeFragment extends Fragment {

  @BindView(R.id.homeRecyclerView) RecyclerView homeRecyclerView;
  @BindView(R.id.homeLoadingGroup) View loadingGroup;
  @BindView(R.id.homeErrorView) View errorView;

  @BindString(R.string.error_occ) String errorMessage;

  @Inject ViewModelFactoryProvider viewModelFactoryProvider;

  private HomeViewModel viewModel;
  private Unbinder unbinder;
  private UsersAdapter adapter = new UsersAdapter();
  private boolean isTablet;

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setupDaggerInjection();
  }

  private void setupDaggerInjection() {
    if (getActivity() == null) return;
    FragmentSubComponent fragmentSubComponent = SimpleApplication.get(getActivity())
        .getApplicationComponent()
        .getFragmentSubComponent()
        .bindsFragmentContext(this)
        .build();
    fragmentSubComponent.bind(this);
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    isTablet = getResources().getBoolean(R.bool.isTablet);
    int layoutId = isTablet ? R.layout.fragment_home_tablet : R.layout.fragment_home;
    View view = inflater.inflate(layoutId, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupViewModel();
    homeRecyclerView.setAdapter(adapter);
    if (isTablet) {
      setupMasterDetailViewView();
    } else {
      setupSingleView(view);
    }
    observerUsersData();
  }

  private void setupViewModel() {
    viewModel = new ViewModelProvider(
        requireActivity(),
        viewModelFactoryProvider
    ).get(HomeViewModel.class);
  }

  private void setupSingleView(View view) {
    Disposable clicksDisposable = adapter.getAdapterClicksObservable()
        .subscribe(userId ->
            Navigation.findNavController(view)
                .navigate(R.id.action_homeFragment_to_detailsFragment, createUserIdBundle(userId))
        );
    viewModel.compositeDisposable.add(clicksDisposable);
  }

  private void setupMasterDetailViewView() {
    NavHostFragment hostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(
        R.id.detail_fragment_container
    );
    if (hostFragment == null) return;
    Disposable clicksDisposable = adapter.getAdapterClicksObservable()
        .subscribe(userId ->
            hostFragment.getNavController()
                .navigate(R.id.detailsFragment, createUserIdBundle(userId))
        );
    viewModel.compositeDisposable.add(clicksDisposable);
    if (getView() == null) return;
    View fragContainer = getView().findViewById(R.id.detail_fragment_container);
    if (fragContainer != null) fragContainer.setVisibility(View.GONE);
  }

  private Bundle createUserIdBundle(long userId) {
    Bundle bundle = new Bundle();
    bundle.putLong(Constants.ARG_USER_ID, userId);
    return bundle;
  }

  private void observerUsersData() {
    viewModel.getUsersState().observe(getViewLifecycleOwner(), listStateData -> {
      switch (listStateData.getStatus()) {
        case LOADING:
          loadingGroup.setVisibility(View.VISIBLE);
          break;
        case SUCCESS:
          loadingGroup.setVisibility(View.GONE);
          if (listStateData.getData() == null || listStateData.getData().isEmpty()) {
            errorView.setVisibility(View.VISIBLE);
            return;
          }
          adapter.updateData(listStateData.getData());
          homeRecyclerView.post(() -> {
            RecyclerView.ViewHolder viewHolder =
                homeRecyclerView.findViewHolderForAdapterPosition(0);
            if (isTablet) {
              if (viewHolder == null) return;
              View itemView = viewHolder.itemView;
              itemView.performClick();
              if (getView() == null) return;
              View fragContainer = getView().findViewById(R.id.detail_fragment_container);
              if (fragContainer != null) fragContainer.setVisibility(View.VISIBLE);
            }
          });
          break;
        case ERROR:
          loadingGroup.setVisibility(View.GONE);
          if (isTablet) {
            if (getView() == null) return;
            View fragContainer = getView().findViewById(R.id.detail_fragment_container);
            if (fragContainer != null) fragContainer.setVisibility(View.GONE);
          }
          if (isAdded()) Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
          break;
      }
    });
  }

  @Override public void onResume() {
    super.onResume();
    if (getActivity() instanceof MainActivity) {
      ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
      if (actionBar != null) {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_burger);
      }
    }
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
