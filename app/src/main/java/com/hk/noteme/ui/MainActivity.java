package com.hk.noteme.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hk.noteme.R;

public class MainActivity extends AppCompatActivity {
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //init
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        frameLayout = findViewById(R.id.frameLayoutID);

        //default navigation tab
        replaceFragment(new OpenTaskFragment());

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_open:
                    replaceFragment(new OpenTaskFragment());

                    return true;
                case R.id.navigation_inProgress:
                    replaceFragment(new ProgressTaskFragment());

                    return true;
                case R.id.navigation_test:
                    replaceFragment(new TestTaskFragment());

                    return true;
                case R.id.navigation_done:
                    replaceFragment(new DoneTaskFragment());

                    return true;
            }
            return false;
        }
    };

    public void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.frameLayoutID, fragment);
        ft.commit();
    }


}