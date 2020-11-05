package com.example.taskmanagerroom.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.taskmanagerroom.model.User;

import java.util.List;
import java.util.UUID;

@Dao
public interface UserDAO {

    @Update
    void updateUser(User user);

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Insert
    void insertUsers(User... users);

    @Query("SELECT * FROM userTable")
    List<User> getUsers();

    @Query("SELECT * FROM userTable WHERE userId=:inputId")
    User getUser(UUID inputId);

    @Query("SELECT * FROM userTable WHERE username LIKE :inputuser")
    User getUser(String inputuser);




}
