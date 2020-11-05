package com.example.taskmanagerroom.controller.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.taskmanagerroom.R;
import com.example.taskmanagerroom.controller.fragment.LoginFragment;
import com.example.taskmanagerroom.controller.fragment.UserListFragment;

public class UserListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragment_container1);


        if (fragment == null) {
            UserListFragment userListFragment = UserListFragment.newInstance();
            fragmentManager
                    .beginTransaction()
                    .add(R.id.fragment_container1, userListFragment)
                    .commit();
        }

    }
}