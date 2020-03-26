package com.simple.list.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.simple.list.utilities.Constants;
import io.reactivex.Single;
import java.util.List;

@Dao
public interface UserDao {

  @Insert void insert(List<UserEntity> userEntities);

  @Update(onConflict = OnConflictStrategy.REPLACE) void update(List<UserEntity> userEntities);

  @Query("SELECT * FROM " + Constants.TABLE_USER) Single<List<UserEntity>> getUsers();

  @Query("SELECT * FROM " + Constants.TABLE_USER + " WHERE " + Constants.TABLE_USER_ID + "= :id")
  Single<UserEntity> getUser(long id);
}
