package com.simple.list.data;

import com.simple.list.data.local.UserDao;
import com.simple.list.data.local.UserEntity;
import com.simple.list.data.remote.UserResponse;
import com.simple.list.data.remote.api.UserApi;
import io.reactivex.Single;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class UsersRepository {

  private UserApi userApi;
  private UserDao userDao;

  @Inject public UsersRepository(UserApi userApi, UserDao userDao) {
    this.userApi = userApi;
    this.userDao = userDao;
  }

  public Single<List<UserEntity>> loadUsersFromLocal() {
    return userDao.getUsers();
  }

  public Single<List<UserEntity>> loadUsersFromRemote(boolean update) {
    return userApi.getUsers()
        .flatMap(userResponses -> {
          List<UserEntity> entities = new ArrayList<>();
          for (UserResponse user : userResponses) {
            entities.add(mapFromUserResponseToUserEntity(user));
          }
          if (update) {
            userDao.update(entities);
          } else {
            userDao.insert(entities);
          }
          return Single.just(entities);
        });
  }

  public Single<UserEntity> getUser(long userId) {
    return userDao.getUser(userId);
  }

  private UserEntity mapFromUserResponseToUserEntity(UserResponse userResponse) {
    UserResponse.Address address = userResponse.getAddress();
    String fullAddress = address.getStreet() + ", " + address.getSuite()
        + ", " + address.getCity();
    return new UserEntity(
        userResponse.getId(), userResponse.getName(), userResponse.getUsername(),
        userResponse.getPhone(), userResponse.getEmail(), userResponse.getWebsite(),
        fullAddress, userResponse.getCompany().getName()
    );
  }
}
