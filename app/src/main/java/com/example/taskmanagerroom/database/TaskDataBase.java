package com.example.taskmanagerroom.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.taskmanagerroom.model.Task;

@Database(entities = Task.class, version = 2)
@TypeConverters({Converters.class})
public abstract class TaskDataBase extends RoomDatabase {

    public abstract TaskDAO getTaskDataBaseDAO();
}
