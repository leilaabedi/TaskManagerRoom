package com.example.taskmanagerroom.repository;

import com.example.taskmanagerroom.model.State;
import com.example.taskmanagerroom.model.Task;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface IRepository extends Serializable {

    List<Task> getTasks();
    Task getSingleTask(UUID taskId);
    void insertTask(Task task);
    void updateTask(Task task);
    void removeSingleTask(Task task);
    void removeTasks();
    List<Task> getTasksList(State state);
    void addTaskToDo(Task task);
    void addTaskDone(Task task);
    void addTaskDoing(Task task);
    File getPhotoFile(Task task);


}
