package com.hk.noteme.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;

import com.hk.noteme.R;
import com.hk.noteme.databinding.ActivityAddTaskBinding;
import com.hk.noteme.models.Task;
import com.hk.noteme.viewmodel.TaskViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity {
    private ActivityAddTaskBinding binding;
    private String email = "none";
    private String phone = "none";
    private String url = "none";
    boolean updateRequest = false;
    private String currentDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
    private Calendar calendar;
    private int year, month, day;
    private TaskViewModel taskViewModel;
    final String[] typeStatus = {"Open", "Progress", "Test", "Done"};
    private String status = "";
    private int selectposition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_task);

        //init
        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        //get update intent
        if (getIntent() != null) {
            updateRequest = true;
            binding.titleET.setText(getIntent().getStringExtra("title"));
            binding.detailET.setText(getIntent().getStringExtra("detail"));
            binding.deadLineTV.setText(getIntent().getStringExtra("deadline"));
            status = getIntent().getStringExtra("status");
            currentDate = getIntent().getStringExtra("create_date");
            email = getIntent().getStringExtra("email");
            phone = getIntent().getStringExtra("phone");
            url = getIntent().getStringExtra("url");
        }

        if (!updateRequest) {
            currentDate = dateFormat.format(new Date());
        }
        binding.deadLineTV.setText(currentDate);

        //calender initialize
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog.OnDateSetListener endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int toDay) {
                calendar.set(year, month, toDay);
                String userFromDate = dateFormat.format(calendar.getTime());
                binding.deadLineTV.setText(userFromDate);

            }
        };

        binding.calenderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, endDateListener, year, month, day);
                datePickerDialog.show();
            }
        });

        setStatus();
        binding.submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.titleET.getText().toString().equals("")) {
                    binding.titleET.setError("title is required");
                    binding.titleET.requestFocus();
                    return;
                } else if (binding.detailET.getText().toString().equals("")) {
                    binding.detailET.setError("detail is required");
                    binding.detailET.requestFocus();
                    return;
                } else {
                    saveData(
                            binding.titleET.getText().toString(),
                            binding.detailET.getText().toString()
                    );
                }
            }
        });

        binding.mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionalItemDialog("mail");
            }
        });
        binding.urlBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionalItemDialog("url");
            }
        });
        binding.phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionalItemDialog("phone");
            }
        });
    }

    private void setStatus() {
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_activated_1, typeStatus);
        binding.statusSpinner.setAdapter(arrayAdapter);


        if (updateRequest) {
            //find position that selected by user
            for (int i = 0; i < typeStatus.length; i++) {
                if (status.equals(typeStatus[i])) {
                    selectposition = i;
                }
            }
            binding.statusSpinner.setSelection(selectposition);//set spinner position thats request for update

        } else {
            status = typeStatus[0];
        }

        binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                status = typeStatus[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void optionalItemDialog(String item) {
        AlertDialog optionItemDialog = new AlertDialog.Builder(AddTaskActivity.this).create();
        optionItemDialog.setCancelable(true);
        final View view = LayoutInflater.from(AddTaskActivity.this).inflate(R.layout.add_optional_item_dialog, null);
        final EditText itemET = view.findViewById(R.id.optionalItemET);
        AppCompatImageView icon = view.findViewById(R.id.optionalItemIV);
        AppCompatButton saveBtn = view.findViewById(R.id.saveBtn);
        switch (item) {
            case "mail":
                if (updateRequest && !email.equals("none")) {
                    itemET.setText(email);
                }
                icon.setImageResource(R.drawable.ic_email);
                saveBtn.setText("Save email");
                break;
            case "url":
                if (updateRequest && !url.equals("none")) {
                    itemET.setText(url);
                }
                icon.setImageResource(R.drawable.ic_url);
                saveBtn.setText("Save url");
                break;
            case "phone":
                if (updateRequest && !phone.equals("none")) {
                    itemET.setText(phone);
                }
                icon.setImageResource(R.drawable.ic_phone);
                saveBtn.setText("Save phone");
                break;
            default:
        }
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!itemET.getText().toString().equals("")) {
                    if (item.equals("mail")) {
                        email = itemET.getText().toString();
                    } else if (item.equals("url")) {
                        url = itemET.getText().toString();
                    } else {
                        phone = itemET.getText().toString();
                    }
                }
                optionItemDialog.dismiss();
            }
        });
        optionItemDialog.setView(view);
        optionItemDialog.show();
    }

    public void saveData(String title, String detail) {
        Task task = new Task(
                title,
                detail,
                status,
                currentDate,
                binding.deadLineTV.getText().toString(),
                url,
                email, phone
        );
        if (updateRequest) {
            //update task data
            taskViewModel.updateTask(task);
            //show dialog
        } else {
            //insert new task data
            taskViewModel.insertTask(task);
            //todo: show inserted dialog
        }
    }
}