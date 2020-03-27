package com.simple.list.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.simple.list.utilities.Constants;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface UserDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE) void insert(List<UserEntity> userEntities);

  @Query("SELECT * FROM " + Constants.TABLE_USER) Single<List<UserEntity>> getUsers();

  @Query("SELECT * FROM " + Constants.TABLE_USER + " WHERE " + Constants.TABLE_USER_ID + "= :id")
  Single<UserEntity> getUser(long id);

  @Query("SELECT COUNT(" + Constants.TABLE_USER_ID + ") FROM " + Constants.TABLE_USER)
  int getUsersCount();
}
