package com.hk.noteme.reepository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.hk.noteme.dao.TaskDao;
import com.hk.noteme.database.TaskDatabase;
import com.hk.noteme.models.Task;

import java.util.List;

public class TaskRepository {
    public TaskDao taskDao;
    LiveData<List<Task>> tasks;
    LiveData<Task> task;

    public TaskRepository(Application application) {
        TaskDatabase db = TaskDatabase.getDatabase(application);
        taskDao = db.taskDao();
        tasks = taskDao.getAllTask();

    }

    public LiveData<List<Task>> getAllTask() {
        return tasks;
    }

    public LiveData<Task> getSingleTask(int id) {
        task = taskDao.getSingleTask(id);
        return task;
    }

    public void insertTask(final Task task) {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.insertTask(task);
            }
        });
    }

    public void updateTask(Task task) {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.updateTask(task);
            }
        });
    }

    public void deleteTask(final Task task) {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteTask(task);
            }
        });
    }

    public void deleteAllTask() {
        TaskDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                taskDao.deleteAll();
            }
        });
    }
}

