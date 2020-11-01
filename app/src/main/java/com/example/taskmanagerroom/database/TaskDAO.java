package com.example.taskmanagerroom.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerroom.model.State;
import com.example.taskmanagerroom.model.Task;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskDAO {
    @Update
    void updateTask(Task task);


    @Insert
    void insertTask(Task task);

    @Delete
    void deleteTask(Task task);

    @Delete
    void deleteTasks(Task... tasks);

    @Insert
    void insertTasks(Task... tasks);

    @Query("SELECT * FROM taskTable WHERE taskstate LIKE :str")
    List<Task> gettypeTasks(String str);



    @Query("SELECT * FROM taskTable")
    List<Task> getTasks();

    @Query("SELECT * FROM taskTable WHERE taskId=:inputId")
    Task getSingleTask(UUID inputId);


}
