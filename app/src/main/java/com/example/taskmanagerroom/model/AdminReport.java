package com.example.taskmanagerroom.model;

import androidx.room.ColumnInfo;

import java.util.Date;
import java.util.UUID;

public class AdminReport
{
    private UUID mUserId;
    private String mUsername;
    private String mUserDate;
    private Integer mCount;

    public AdminReport() {
    }




    public AdminReport(UUID mUserId, String mUsername, String mUserDate, Integer mCount) {
        this.mUserId = mUserId;
        this.mUsername = mUsername;
        this.mUserDate = mUserDate;
        this.mCount = mCount;
    }

    public UUID getUserId() {
        return mUserId;
    }

    public void setUserId(UUID mUserId) {
        this.mUserId = mUserId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getUserDate() {
        return mUserDate;
    }

    public void setUserDate(String mUserDate) {
        this.mUserDate = mUserDate;
    }

    public Integer getCount() {
        return mCount;
    }

    public void setCount(Integer mCount) {
        this.mCount = mCount;
    }
}
