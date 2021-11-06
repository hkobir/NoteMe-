package com.hk.noteme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.hk.noteme.R;
import com.hk.noteme.databinding.ActivityAddTaskBinding;
import com.hk.noteme.databinding.ActivityTaskDetailBinding;

public class TaskDetailActivity extends AppCompatActivity {
    private ActivityTaskDetailBinding binding;
    private String email = "none";
    private String phone = "none";
    private String url = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_task_detail);
//get update intent
        if (getIntent() != null) {
            binding.titleTV.setText(getIntent().getStringExtra("title"));
            binding.descriptionTV.setText(getIntent().getStringExtra("detail"));
            binding.deadLineTV.setText(getIntent().getStringExtra("deadline"));
            binding.createDateTV.setText(getIntent().getStringExtra("create_date"));
            binding.statusTV.setText(getIntent().getStringExtra("status"));


            email = getIntent().getStringExtra("email");
            phone = getIntent().getStringExtra("phone");
            url = getIntent().getStringExtra("url");
        }

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
                OptionalItemSheet sheet = new OptionalItemSheet(email, "url");
                sheet.show(getSupportFragmentManager(), "sheet");
            }

        });
        binding.phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OptionalItemSheet sheet = new OptionalItemSheet(email, "phone");
                sheet.show(getSupportFragmentManager(), "sheet");
            }
        });
    }
}