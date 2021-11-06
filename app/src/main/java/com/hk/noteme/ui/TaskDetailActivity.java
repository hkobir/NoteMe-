package com.hk.noteme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.hk.noteme.R;
import com.hk.noteme.databinding.ActivityAddTaskBinding;
import com.hk.noteme.databinding.ActivityTaskDetailBinding;
import com.hk.noteme.models.Task;
import com.hk.noteme.viewmodel.TaskViewModel;

public class TaskDetailActivity extends AppCompatActivity {
    private ActivityTaskDetailBinding binding;
    private String email = "none";
    private String phone = "none";
    private String url = "none";
    private int id;
    TaskViewModel viewModel;
    private AlertDialog deleteDialog;
    private Task taskNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_detail);
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);
//get update intent
        if (getIntent().getIntExtra("id", -1) != -1) {
            id = getIntent().getIntExtra("id", 0);
        }

        setTaskData();
        binding.mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionalItemSheet sheet = new OptionalItemSheet(email, "mail");
                sheet.show(getSupportFragmentManager(), "sheet");
            }
        });
        binding.urlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionalItemSheet sheet = new OptionalItemSheet(url, "url");
                sheet.show(getSupportFragmentManager(), "sheet");
            }

        });
        binding.phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionalItemSheet sheet = new OptionalItemSheet(phone, "phone");
                sheet.show(getSupportFragmentManager(), "sheet");
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.editTaskFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskDetailActivity.this, AddTaskActivity.class);
                intent.putExtra(
                        "id",
                        id
                );
                intent.putExtra(
                        "title",
                        taskNote.getTaskName()
                );
                intent.putExtra(
                        "detail",
                        taskNote.getTaskDetail()
                );
                intent.putExtra(
                        "deadline",
                        taskNote.getDeadLine()
                );
                intent.putExtra(
                        "status",
                        taskNote.getTaskStatus()
                );
                intent.putExtra(
                        "create_date",
                        taskNote.getCreateDate()
                );
                intent.putExtra("email", email);
                intent.putExtra("phone", phone);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });
        binding.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDialog = new AlertDialog.Builder(TaskDetailActivity.this).create();
                deleteDialog.setCancelable(false);
                final View view = LayoutInflater.from(TaskDetailActivity.this).inflate(R.layout.delete_task_confirm_dialogue, null);
                Button deleteBtn = view.findViewById(R.id.deleteButon);
                AppCompatButton cancelBtn = view.findViewById(R.id.cancelButon);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Task task = new Task(
                                taskNote.getTaskName(),
                                taskNote.getTaskDetail(),
                                taskNote.getTaskStatus(),
                                taskNote.getCreateDate(),
                                taskNote.getDeadLine(),
                                url,
                                email,
                                phone

                        );
                        task.setId(id);
                        viewModel.deleteTask(task);
                        deleteDialog.dismiss();
                        finish();
                    }
                });

                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deleteDialog.dismiss();
                    }
                });

                deleteDialog.setView(view);
                deleteDialog.show();
            }

        });
    }

    private void setTaskData() {
        viewModel.getSingleTask(id).observe(this, new Observer<Task>() {
            @Override
            public void onChanged(Task task) {
                if (task != null) {

                    taskNote = task;
                    binding.titleTV.setText(task.getTaskName());
                    binding.descriptionTV.setText(task.getTaskDetail());
                    binding.deadLineTV.setText(task.getDeadLine());
                    binding.createDateTV.setText(task.getCreateDate());
                    binding.statusTV.setText(task.getTaskStatus());


                    email = task.getMail();
                    phone = task.getPhone();
                    url = task.getUrl();
                }
            }
        });
    }

}