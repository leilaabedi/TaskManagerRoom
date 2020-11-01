package com.example.taskmanagerroom.repository;

import android.content.Context;

import androidx.room.Room;

import com.example.taskmanagerroom.database.TaskDAO;
import com.example.taskmanagerroom.database.TaskDataBase;
import com.example.taskmanagerroom.model.State;
import com.example.taskmanagerroom.model.Task;
import com.example.taskmanagerroom.model.User;

import java.io.File;
import java.util.List;
import java.util.UUID;

public class TaskDBRepository implements IRepository {


    private static TaskDBRepository sInstance;

    //    private SQLiteDatabase mDatabase;
    private TaskDAO mTaskDAO;
    private Context mContext;

    public static TaskDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new TaskDBRepository(context);

        return sInstance;
    }

    private TaskDBRepository(Context context) {
        mContext = context.getApplicationContext();

        TaskDataBase taskDataBase = Room.databaseBuilder(mContext,
                TaskDataBase.class,
                "task.db")
                .allowMainThreadQueries()
                .build();
        // dao is interface, so you should create database first and then get database.
        mTaskDAO = taskDataBase.getTaskDataBaseDAO();

    }


    @Override
    public List<Task> getTasks() {
        return mTaskDAO.getTasks();
    }

    @Override
    public Task getSingleTask(UUID taskId) {
        return mTaskDAO.getSingleTask(taskId);
    }

    @Override
    public void insertTask(Task task) {
        mTaskDAO.insertTask(task);

    }

    @Override
    public void updateTask(Task task) {
        mTaskDAO.updateTask(task);

    }

    @Override
    public void removeSingleTask(Task task) {
        mTaskDAO.deleteTask(task);

    }

    @Override
    public void removeTasks() {
        mTaskDAO.deleteTasks();

    }

    @Override
    public List<Task> getTasksList(State tempstate) {
        String result = "";

        if (tempstate == State.DOING)
            result = "DOING";

        if (tempstate == State.DONE)
            result = "DONE";

        if (tempstate == State.TODO)
            result = "TODO";




        return mTaskDAO.gettypeTasks(result);
    }



    @Override
    public void addTaskToDo(Task task) {
        mTaskDAO.insertTask(task);

    }

    @Override
    public void addTaskDone(Task task) {
        mTaskDAO.insertTask(task);

    }

    @Override
    public void addTaskDoing(Task task) {
        mTaskDAO.insertTask(task);

    }
    public File getPhotoFile(Task task) {
        // /data/data/com.example.criminalintent/files/
        File filesDir = mContext.getFilesDir();

        // /data/data/com.example.criminalintent/files/IMG_ktui4u544nmkfuy48485.jpg
        File photoFile = new File(filesDir, task.getPhotoFileName());
        return photoFile;
    }


}
