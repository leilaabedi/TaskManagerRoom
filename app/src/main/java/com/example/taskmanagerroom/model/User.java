package com.example.taskmanagerroom.model;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Entity(tableName = "userTable")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long primaryId;

    @ColumnInfo(name = "userId")
    private UUID mId;
    @ColumnInfo(name = "username")
    private String mUserName;
    @ColumnInfo(name = "password")
    private String mPassword;
    @ColumnInfo(name = "memDate")
    private String mDate;

    public User() {
        this(UUID.randomUUID());
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM YYYY");
        Date d = new Date();
        mDate = sdf.format(d);
        Log.i("date", mDate);

    }


    public User(UUID id, String userName, String password) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM YYYY");
        Date d = new Date();
        mId = id;
        mUserName = userName;
        mPassword = password;
        mDate = sdf.format(d);
        Log.i("date", mDate);
    }

    public User(String userName, String password) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM YYYY");
        Date d = new Date();
        mId = UUID.randomUUID();
        mUserName = userName;
        mPassword = password;
        mDate = sdf.format(d);

        Log.i("date", mDate);


    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }


    public User(UUID id) {
        mId = id;
    }


    //Getter & Setters
    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }


    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }
}
