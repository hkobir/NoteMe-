package com.hk.noteme.ui;

import static androidx.databinding.DataBindingUtil.inflate;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hk.noteme.R;
import com.hk.noteme.adapters.TaskListAdapter;
import com.hk.noteme.databinding.FragmentProgressTaskBinding;
import com.hk.noteme.models.Task;
import com.hk.noteme.viewmodel.TaskViewModel;

import java.util.ArrayList;
import java.util.List;


public class ProgressTaskFragment extends Fragment  {
    private FragmentProgressTaskBinding binding;
    private Context context;
    private TaskViewModel taskViewModel;
    private List<Task> taskList;
    private TaskListAdapter taskAdapter;

    public ProgressTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = inflate(inflater, R.layout.fragment_progress_task, container, false);

        context = container.getContext();
        //init
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskList = new ArrayList<>();
        taskAdapter = new TaskListAdapter(context);
        binding.progressTaskRV.setLayoutManager(new LinearLayoutManager(context));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        populateData();
        binding.addTaskFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddTaskActivity.class);
                intent.putExtra("status_position", 1);
                startActivity(intent);
            }
        });
    }

    public void populateData() {
        taskViewModel.getAllTask().observe(getActivity(), new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {
                taskList.clear();
                for (Task task : tasks) {
                    if (task.getTaskStatus().equals("Progress")) {
                        taskList.add(task);
                    }
                }

                binding.emptyTV.setVisibility(View.GONE);
                //empty item
                if (taskList.size() <= 0) {
                    binding.emptyTV.setVisibility(View.VISIBLE);
                }
                Log.d("taskData", tasks.toString());
                taskAdapter.setTaskList(taskList);
                binding.progressTaskRV.setAdapter(taskAdapter);
            }
        });
    }

}