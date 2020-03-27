package com.simple.list.presentation.ui.fragment.home;

import androidx.lifecycle.ViewModel;
import com.simple.list.data.UsersRepository;
import com.simple.list.data.local.UserEntity;
import com.simple.list.presentation.ui.StateLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import javax.inject.Inject;
import timber.log.Timber;

public class HomeViewModel extends ViewModel {

  CompositeDisposable compositeDisposable = new CompositeDisposable();

  private UsersRepository usersRepository;

  @Inject public HomeViewModel(UsersRepository usersRepository) {
    this.usersRepository = usersRepository;
  }

  private StateLiveData<List<UserEntity>> usersState;

  public StateLiveData<List<UserEntity>> getUsersState() {
    if (usersState == null) {
      usersState = new StateLiveData<>();
      loadUsers();
    }
    return usersState;
  }

  private void loadUsers() {
    Disposable usersDisposable = usersRepository.loadUsersList()
        .doOnSubscribe(disposable -> usersState.postLoading())
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(users -> usersState.postSuccess(users), Timber::e);
    compositeDisposable.add(usersDisposable);
  }

  @Override protected void onCleared() {
    if (!compositeDisposable.isDisposed()) compositeDisposable.dispose();
  }
}
