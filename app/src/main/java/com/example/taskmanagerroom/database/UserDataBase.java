package com.example.taskmanagerroom.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanagerroom.model.Task;
import com.example.taskmanagerroom.model.User;

@Database(entities = User.class, version = 2)
@TypeConverters({Converters.class})
public abstract class UserDataBase extends RoomDatabase {

    public abstract UserDAO getUserDataBaseDAO();
}
