package com.simple.list.presentation.ui.fragment.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.simple.list.R;
import com.simple.list.data.local.UserEntity;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserViewHolder> {

  private List<UserEntity> users = new ArrayList<>();
  private final PublishSubject<Long> onClickSubject = PublishSubject.create();

  @NonNull @Override
  public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
    return new UserViewHolder(item);
  }

  @Override public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
    Context context = holder.itemView.getContext();
    UserEntity user = users.get(position);
    holder.name.setText(user.getName());
    holder.address.setText(user.getAddress());
    holder.phone.setText(context.getString(R.string.user_item_phone, user.getPhone()));
  }

  public void updateData(List<UserEntity> users) {
    this.users.clear();
    this.users.addAll(users);
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return users.size();
  }

  public Observable<Long> getAdapterClicksObservable() {
    return onClickSubject;
  }

  class UserViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.itemUsername) TextView name;
    @BindView(R.id.itemUserAddress) TextView address;
    @BindView(R.id.itemUserPhone) TextView phone;

    public UserViewHolder(@NonNull View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }

    @OnClick public void onClick() {
      onClickSubject.onNext(users.get(getAdapterPosition()).getId());
    }
  }
}
