package com.simple.list.data;

import com.simple.list.data.local.UserDao;
import com.simple.list.data.local.UserEntity;
import com.simple.list.data.remote.UserResponse;
import com.simple.list.data.remote.api.UserApi;
import com.simple.list.utilities.NetworkUtils;
import io.reactivex.Observable;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class UsersRepository {

  private NetworkUtils networkUtils;
  private UserApi userApi;
  private UserDao userDao;

  @Inject public UsersRepository(NetworkUtils networkUtils, UserApi userApi, UserDao userDao) {
    this.networkUtils = networkUtils;
    this.userApi = userApi;
    this.userDao = userDao;
  }

  @SuppressWarnings("unchecked")
  public Observable<List<UserEntity>> loadUsersList() {
    Observable<List<UserEntity>> remoteSource = null;
    if (networkUtils.isNetworkAvailable()) remoteSource = loadUsersFromRemote().toObservable();
    Observable<List<UserEntity>> localSource = loadUsersFromLocal().toObservable();
    if (networkUtils.isNetworkAvailable()) {
      return Observable.concatArrayEager(remoteSource, localSource);
    } else {
      return localSource;
    }
  }

  private Single<List<UserEntity>> loadUsersFromLocal() {
    return userDao.getUsers();
  }

  private Single<List<UserEntity>> loadUsersFromRemote() {
    return userApi.getUsers()
        .flatMap(userResponses -> {
          List<UserEntity> entities = new ArrayList<>();
          for (UserResponse user : userResponses) {
            entities.add(mapFromUserResponseToUserEntity(user));
          }
          userDao.insert(entities);
          return Single.just(entities);
        });
  }

  public Single<UserEntity> getUser(long userId) {
    return userDao.getUser(userId);
  }

  private UserEntity mapFromUserResponseToUserEntity(UserResponse userResponse) {
    UserResponse.Address address = userResponse.getAddress();
    String fullAddress = address.getStreet()
        + ", "
        + address.getSuite()
        + ", "
        + address.getCity();
    return new UserEntity(
        userResponse.getId(), userResponse.getName(), userResponse.getUsername(),
        userResponse.getPhone(), userResponse.getEmail(), userResponse.getWebsite(),
        fullAddress, userResponse.getCompany().getName()
    );
  }
}
