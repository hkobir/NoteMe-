package com.hk.noteme.viewmodel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.hk.noteme.models.Task;
import com.hk.noteme.reepository.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository taskRepository;
    private LiveData<List<Task>> tasks;
    private LiveData<Task> task;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        taskRepository = new TaskRepository(application);
        tasks = taskRepository.getAllTask();
    }

    public LiveData<Task> getSingleTask(int id) {
        task = taskRepository.getSingleTask(id);
        return task;
    }

    public LiveData<List<Task>> getAllTask() {
        return tasks;
    }

    public void insertTask(Task task) {
        taskRepository.insertTask(task);
    }

    public void deleteTask(Task task) {
        taskRepository.deleteTask(task);
    }

    public void deleteAllTask() {
        taskRepository.deleteAllTask();
    }

    public void updateTask(Task task) {
        taskRepository.updateTask(task);
    }
}
