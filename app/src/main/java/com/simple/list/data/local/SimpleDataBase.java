package com.simple.list.data.local;

import android.app.Application;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.simple.list.utilities.Constants;

@Database(entities = { UserEntity.class }, version = 1, exportSchema = false)
public abstract class SimpleDataBase extends RoomDatabase {

  public static SimpleDataBase get(Application application) {
    return Room.databaseBuilder(application, SimpleDataBase.class, Constants.DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .build();
  }

  public abstract UserDao getUserDao();
}
