package com.example.taskmanagerroom.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.room.Room;

import com.example.taskmanagerroom.database.TaskDAO;
import com.example.taskmanagerroom.database.TaskDataBase;
import com.example.taskmanagerroom.database.UserDAO;
import com.example.taskmanagerroom.database.UserDataBase;
import com.example.taskmanagerroom.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class UserDBRepository implements Serializable {

    private static UserDBRepository sInstance;

    //    private SQLiteDatabase mDatabase;
    private UserDAO mUserDAO;
    private Context mContext;

    public static UserDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new UserDBRepository(context);
        return sInstance;
    }

    private UserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        UserDataBase userDataBase = Room.databaseBuilder(mContext,
               UserDataBase.class,
                "user.db")
                .allowMainThreadQueries()
                .build();
        // dao is interface, so you should create database first and then get database.
        mUserDAO= userDataBase.getUserDataBaseDAO();
    }

    public List<User> getUsers() {
       return mUserDAO.getUsers();
    }

    public User getUser(UUID userId) {
        return  mUserDAO.getUser(userId);
    }

    public User getUser(String  userName) {
        return  mUserDAO.getUser(userName);
    }

    public Boolean searchUser(User user) {
        List<User> users = getUsers();
        String username = user.getUserName();
        String password = user.getPassword();
        for (int i = 0; i < users.size() ; i++) {
            if (users.get(i).getUserName().equals(username) &&
                    users.get(i).getPassword().equals(password)){
                return true;
            }
        }
        return false;
    }

    public void insertUser(User user) {
        mUserDAO.insertUser(user);
    }

    public void deleteUser(UUID uId){
        List<User> users = getUsers();
        User tempUser=new User();

        for (int i = 0; i < users.size() ; i++) {
            if (users.get(i).getId().equals(uId)){
                tempUser=users.get(i);
            }
        }
        mUserDAO.deleteUser(tempUser);

    }






}




