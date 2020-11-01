package com.example.taskmanagerroom.controller.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;


import com.example.taskmanagerroom.R;
import com.example.taskmanagerroom.controller.fragment.ChangeTaskFragment;
import com.example.taskmanagerroom.controller.fragment.TaskDetailFragment;
import com.example.taskmanagerroom.controller.fragment.TaskListFragment;
import com.example.taskmanagerroom.model.State;
import com.example.taskmanagerroom.model.Task;
import com.example.taskmanagerroom.model.User;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;


public class PagerActivity extends AppCompatActivity implements
        ChangeTaskFragment.Callbacks{

    private static User mUser;


    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    public static PageAdapter mPageAdapter;

    List<State> mTaskState = new ArrayList<State>() {{
        add(State.TODO);
        add(State.DOING);
        add(State.DONE);
    }};


    public static Intent newIntent(Context context, User user) {
        Intent intent = new Intent(context, PagerActivity.class);
        mUser=user;
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);

        findViews();
        initView();
    }


    private void findViews() {
        mViewPager = findViewById(R.id.viewPager);
        mTabLayout = findViewById(R.id.tabLayout);
    }

    private void initView() {
        mPageAdapter = new PageAdapter(this);
        mViewPager.setAdapter(mPageAdapter);

       // if (!mUser.getUserName().equals("admin")) {

            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator
                    (mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            switch (position) {
                                case 0: {
                                    tab.setText("TODO");
                                    break;
                                }
                                case 1: {
                                    tab.setText("DOING");
                                    break;
                                }
                                case 2: {
                                    tab.setText("DONE");
                                    break;
                                }
                            }
                        }
                    });
            tabLayoutMediator.attach();
       // }
/*
        if (mUser.getUserName().equals("admin")) {

            TabLayoutMediator tabLayoutMediator = new TabLayoutMediator
                    (mTabLayout, mViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
                        @Override
                        public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                            switch (position) {
                                case 0: {
                                    tab.setText("TODO");
                                    break;
                                }
                                case 1: {
                                    tab.setText("DOING");
                                    break;
                                }
                                case 2: {
                                    tab.setText("DONE");
                                    break;
                                }
                                case 3: {
                                    tab.setText("Users");
                                    break;
                                }
                            }
                        }
                    });
            tabLayoutMediator.attach();
        }

*/


        mViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                mPageAdapter.mFragmentList.get(position).updateUI(mTaskState.get(position));
            }

        });

    }


    private class PageAdapter extends FragmentStateAdapter {


        private List<TaskListFragment> mFragmentList = new ArrayList<TaskListFragment>() {{
            add(TaskListFragment.newInstance(mTaskState.get(0),mUser));
            add(TaskListFragment.newInstance(mTaskState.get(1),mUser));
            add(TaskListFragment.newInstance(mTaskState.get(2),mUser));
        }};



        public PageAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return TaskListFragment.newInstance(mTaskState.get(position),mUser);
        }

        @Override
        public int getItemCount() {
            return 3;
        }


    }


    @Override
    protected void onResume() {
        super.onResume();
        mPageAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPageAdapter.notifyDataSetChanged();
    }

    public void onTaskUpdated(Task task) {
        TaskListFragment taskListFragment;

     //   taskListFragment=TaskListFragment.newInstance(task.getTaskState());

        taskListFragment = (TaskListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        taskListFragment.updateUI(task.getTaskState());
    }

}
