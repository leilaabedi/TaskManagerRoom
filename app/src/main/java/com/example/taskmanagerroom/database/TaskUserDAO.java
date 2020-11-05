package com.example.taskmanagerroom.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.taskmanagerroom.model.TaskUser;
import com.example.taskmanagerroom.model.User;
import com.example.taskmanagerroom.model.UserCount;

import java.util.List;
import java.util.UUID;

@Dao
public interface TaskUserDAO {

    @Update
    void updateUser(TaskUser taskUser);

    @Insert
    void insertUser(TaskUser taskUser);

    @Delete
    void deleteUser(TaskUser taskUser);

    @Insert
    void insertUsers(TaskUser... taskUsers);



    @Query("SELECT * FROM TaskUserTable")
    List<TaskUser> getUserTasks();

    @Query("SELECT * FROM TaskUserTable WHERE userId=:inputId")
    List<TaskUser> getUserTask(UUID inputId);


    @Query("SELECT userId,count(*)  as count FROM TaskUserTable Group by userId")
    List<UserCount> getUserCount();


}
