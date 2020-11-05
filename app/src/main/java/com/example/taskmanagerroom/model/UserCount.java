package com.example.taskmanagerroom.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "userCount")
public class UserCount implements Serializable {


    @ColumnInfo(name = "userId")
    private UUID mId;
    @ColumnInfo(name = "count")
    private Integer mCount;



    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }

    public Integer getCount() {
        return mCount;
    }

    public void setCount(Integer mCount) {
        this.mCount = mCount;
    }
}