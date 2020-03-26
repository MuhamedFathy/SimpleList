package com.simple.list.presentation.ui.fragment.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import com.simple.list.R;
import com.simple.list.SimpleApplication;
import com.simple.list.data.local.UserEntity;
import com.simple.list.di.presentation.fragment.FragmentSubComponent;
import com.simple.list.di.presentation.viewmodel.ViewModelFactoryProvider;
import com.simple.list.utilities.Constants;
import javax.inject.Inject;

public class DetailsFragment extends Fragment {

  @BindView(R.id.detailsRoot) View rootView;
  @BindView(R.id.detailName) TextView name;
  @BindView(R.id.detailUsername) TextView username;
  @BindView(R.id.detailAddress) TextView address;
  @BindView(R.id.detailEmail) TextView email;
  @BindView(R.id.detailPhone) TextView phone;
  @BindView(R.id.detailWebsite) TextView website;
  @BindView(R.id.detailCompany) TextView company;
  @BindView(R.id.detailsLoadingGroup) View loadingGroup;
  @BindView(R.id.detailErrorView) View errorView;

  @BindString(R.string.error_occ) String errorMessage;

  @Inject ViewModelFactoryProvider viewModelFactoryProvider;

  private DetailsViewModel viewModel;
  private Unbinder unbinder;

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
    View view = inflater.inflate(R.layout.fragment_details, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupViewModel();
    if (getArguments() != null) observerUserData(getArguments().getLong(Constants.ARG_USER_ID));
  }

  private void setupViewModel() {
    viewModel = new ViewModelProvider(
        requireActivity(),
        viewModelFactoryProvider
    ).get(DetailsViewModel.class);
  }

  private void observerUserData(long userId) {
    viewModel.getUserState(userId).observe(getViewLifecycleOwner(), userStateData -> {
      switch (userStateData.getStatus()) {
        case LOADING:
          loadingGroup.setVisibility(View.VISIBLE);
          break;
        case SUCCESS:
          loadingGroup.setVisibility(View.GONE);
          UserEntity user = userStateData.getData();
          if (user == null) {
            if (isAdded()) Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
            return;
          }
          name.setText(user.getName());
          username.setText(getString(R.string.detail_username, user.getUsername()));
          address.setText(getString(R.string.detail_address, user.getAddress()));
          email.setText(getString(R.string.detail_email, user.getEmail()));
          phone.setText(getString(R.string.detail_phone, user.getPhone()));
          website.setText(getString(R.string.detail_website, user.getWebsite()));
          company.setText(getString(R.string.detail_company, user.getCompanyName()));
          break;
        case ERROR:
          loadingGroup.setVisibility(View.GONE);
          if (isAdded()) Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
          break;
      }
    });
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
  }
}
