package com.example.taskmanagerroom.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.UUID;

@Entity(tableName = "TaskUserTable")
public class TaskUser  {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long primaryId;

    @ColumnInfo(name = "taskId")
    private UUID mTaskID;

    @ColumnInfo(name = "userId")
    private UUID mId;



    public TaskUser( UUID mTaskID, UUID mId) {

        this.mTaskID = mTaskID;
        this.mId = mId;
    }

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public UUID getTaskID() {
        return mTaskID;
    }

    public void setTaskID(UUID mTaskID) {
        this.mTaskID = mTaskID;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID mId) {
        this.mId = mId;
    }
}
