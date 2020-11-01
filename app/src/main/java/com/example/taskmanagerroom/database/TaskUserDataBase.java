package com.example.taskmanagerroom.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanagerroom.model.TaskUser;

@Database(entities = TaskUser.class, version = 1)
@TypeConverters({Converters.class})

public abstract class TaskUserDataBase extends RoomDatabase {

    public abstract TaskUserDAO getTaskUserDataBaseDAO();
}
