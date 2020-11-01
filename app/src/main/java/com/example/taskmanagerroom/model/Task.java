package com.example.taskmanagerroom.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "taskTable")
public class Task implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long primaryId;

    @ColumnInfo(name = "taskId")
    private UUID mTaskID;
    @ColumnInfo(name = "title")
    private String mTaskTitle;
    @ColumnInfo(name = "desc")
    private String mTaskDescription;
    @ColumnInfo(name = "taskstate")
    private State mTaskState;
    @ColumnInfo(name = "taskdate")
    private Date mTaskDate;
   // @ColumnInfo(name = "format")
    //private String mFormat;

    public Task() {
        mTaskID = UUID.randomUUID();
        mTaskDate = new Date();
    }

    public Task(UUID id, String title, String description, State state, Date date) {
        mTaskID = id;
        mTaskTitle = title;
        mTaskDescription = description;
        mTaskState = state;
        mTaskDate = date;
    }


    public Task(UUID id, String title, String description, State state) {
        mTaskID = id;
        mTaskTitle = title;
        mTaskDescription = description;
        mTaskState = state;
    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public void setTaskID(UUID mTaskID) {
        this.mTaskID = mTaskID;
    }

    public UUID getTaskID() {
        return mTaskID;
    }

    public String getTaskTitle() {
        return mTaskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        mTaskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return mTaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        mTaskDescription = taskDescription;
    }

    public State getTaskState() {
        return mTaskState;
    }

    public void setTaskState(State taskState) {
        mTaskState = taskState;
    }

    public Date getTaskDate() {
        return mTaskDate;
    }

    public void setTaskDate(Date taskDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(taskDate);
        int year = calendar.get(Calendar.YEAR);
        int monthOfYear = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, monthOfYear, dayOfMonth, mTaskDate.getHours(), mTaskDate.getMinutes(), mTaskDate.getSeconds());
        mTaskDate = calendar.getTime();
    }

    public void setTaskTime(Date taskTime) {
        mTaskDate.setHours(taskTime.getHours());
        mTaskDate.setMinutes(taskTime.getMinutes());
        mTaskDate.setSeconds(taskTime.getSeconds());
    }

/*
    public SimpleDateFormat getFormat() {
        return mFormat;
    }

    public void setFormat(SimpleDateFormat format) {
        mFormat = format;
    }

 */

    public String getJustDate() {
        SimpleDateFormat mFormat;
        mFormat = new SimpleDateFormat("dd MMM yyyy");
        return mFormat.format(mTaskDate);
    }

    public String getJustTime() {
        SimpleDateFormat mFormat;
        mFormat = new SimpleDateFormat("hh:mm a");
        return mFormat.format(mTaskDate);
    }

    public String getPhotoFileName() {
        return "IMG_" + getTaskID().toString() + ".jpg";
    }

}
