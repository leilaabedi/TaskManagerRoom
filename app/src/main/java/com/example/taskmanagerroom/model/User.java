package com.example.taskmanagerroom.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "userTable")
public class User implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long primaryId;

    @ColumnInfo(name = "uuid")
    private UUID mId;
    @ColumnInfo(name = "username")
    private String mUserName;
    @ColumnInfo(name = "password")
    private String mPassword;

    public User(UUID id, String userName, String password) {
        mId = id;
        mUserName = userName;
        mPassword = password;
    }

    public User(String userName, String password) {
        mId = UUID.randomUUID();
        mUserName = userName;
        mPassword = password;
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

    public User() {
        this(UUID.randomUUID());
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
}