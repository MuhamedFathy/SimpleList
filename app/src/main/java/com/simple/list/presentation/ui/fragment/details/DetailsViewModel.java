package com.simple.list.presentation.ui.fragment.details;

import androidx.lifecycle.ViewModel;
import com.simple.list.data.UsersRepository;
import com.simple.list.data.local.UserEntity;
import com.simple.list.presentation.ui.StateLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import javax.inject.Inject;
import timber.log.Timber;

public class DetailsViewModel extends ViewModel {

  CompositeDisposable compositeDisposable = new CompositeDisposable();

  private UsersRepository usersRepository;

  @Inject public DetailsViewModel(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  private StateLiveData<UserEntity> userState;

  public StateLiveData<UserEntity> getUserState(long userId) {
    if (userState == null) userState = new StateLiveData<>();
    getUserInfo(userId);
    return userState;
  }

  private void getUserInfo(long userId) {
    Disposable usersDisposable = usersRepository.getUser(userId)
        .doOnSubscribe(disposable -> userState.postLoading())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(user -> userState.postSuccess(user), throwable -> {
          userState.postError(throwable);
          Timber.e(throwable);
        });
    compositeDisposable.add(usersDisposable);
  }

  @Override protected void onCleared() {
    if (!compositeDisposable.isDisposed()) compositeDisposable.clear();
  }
}
