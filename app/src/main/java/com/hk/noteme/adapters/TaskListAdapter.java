package com.hk.noteme.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.hk.noteme.R;
import com.hk.noteme.models.Task;
import com.hk.noteme.ui.AddTaskActivity;
import com.hk.noteme.ui.TaskDetailActivity;
import com.hk.noteme.viewmodel.TaskViewModel;

import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private List<Task> taskList;
    private Context context;
    TaskViewModel viewModel;
    private AlertDialog deleteDialog;


    public TaskListAdapter(Context context) {
        this.context = context;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListAdapter.ViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.bind(task);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TaskDetailActivity.class);
                intent.putExtra("id", task.getId());
                context.startActivity(intent);
            }
        });

        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddTaskActivity.class);
                intent.putExtra("id", task.getId());
                intent.putExtra("title", task.getTaskName());
                intent.putExtra("detail", task.getTaskDetail());
                intent.putExtra("deadline", task.getDeadLine());
                intent.putExtra("status", task.getTaskStatus());
                intent.putExtra("create_date", task.getCreateDate());
                intent.putExtra("email", task.getMail());
                intent.putExtra("phone", task.getPhone());
                intent.putExtra("url", task.getUrl());
                context.startActivity(intent);
            }

        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel = new ViewModelProvider((FragmentActivity) context).get(TaskViewModel.class);

                deleteDialog = new AlertDialog.Builder(context).create();
                deleteDialog.setCancelable(false);
                final View view = LayoutInflater.from(context).inflate(R.layout.delete_task_confirm_dialogue, null);
                Button deleteBtn = view.findViewById(R.id.deleteButon);
                AppCompatButton cancelBtn = view.findViewById(R.id.cancelButon);
                deleteBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.deleteTask(task);
                        notifyDataSetChanged();
                        deleteDialog.dismiss();
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

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        AppCompatTextView title, created, deadLine;
        AppCompatImageView editBtn, deleteBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTV);
            created = itemView.findViewById(R.id.createDateTV);
            deadLine = itemView.findViewById(R.id.deadLineTV);
            editBtn = itemView.findViewById(R.id.editBtn);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }

        public void bind(Task task) {
            title.setText(task.getTaskName());
            created.setText(task.getCreateDate());
            deadLine.setText(task.getDeadLine());
        }
    }
}
