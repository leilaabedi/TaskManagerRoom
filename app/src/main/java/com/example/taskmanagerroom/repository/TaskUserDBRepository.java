package com.example.taskmanagerroom.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanagerroom.database.TaskDAO;
import com.example.taskmanagerroom.database.TaskDataBase;

import com.example.taskmanagerroom.database.TaskUserDAO;
import com.example.taskmanagerroom.database.TaskUserDataBase;
import com.example.taskmanagerroom.model.State;
import com.example.taskmanagerroom.model.Task;
import com.example.taskmanagerroom.model.TaskUser;
import com.example.taskmanagerroom.model.User;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public class TaskUserDBRepository implements Serializable {

    private static TaskUserDBRepository sInstance;

    //    private SQLiteDatabase mDatabase;
    private TaskUserDAO mTaUsDAO;

    private Context mContext;

    public static TaskUserDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new TaskUserDBRepository(context);
        return sInstance;
    }

    private TaskUserDBRepository(Context context) {
        mContext = context.getApplicationContext();
        TaskUserDataBase taskUserDataBase = Room.databaseBuilder(mContext,
                TaskUserDataBase.class,
                "TaskUser.db")
                .allowMainThreadQueries()
                .build();




        // dao is interface, so you should create database first and then get database.
        mTaUsDAO = taskUserDataBase.getTaskUserDataBaseDAO();

    }

    public List<TaskUser> getAllUserTask() {
        return mTaUsDAO.getUserTasks();
    }

    public List<TaskUser> getUserTask(UUID userId) {
        return mTaUsDAO.getUserTask(userId);
    }

    public void insertTask(TaskUser taskUser) {
        mTaUsDAO.insertUser(taskUser);

    }



}