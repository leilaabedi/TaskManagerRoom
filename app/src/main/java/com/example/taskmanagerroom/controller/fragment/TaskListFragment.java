package com.example.taskmanagerroom.controller.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerroom.R;
import com.example.taskmanagerroom.controller.activity.MainActivity;
import com.example.taskmanagerroom.controller.activity.UserListActivity;
import com.example.taskmanagerroom.model.State;
import com.example.taskmanagerroom.model.Task;
import com.example.taskmanagerroom.model.TaskUser;
import com.example.taskmanagerroom.model.User;
import com.example.taskmanagerroom.repository.TaskDBRepository;
import com.example.taskmanagerroom.repository.TaskUserDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class TaskListFragment extends Fragment {

    public static final String TASK_DETAIL_FRAGMENT = "taskDetailFragment";
    public static final String CHANGE_TASK_FRAGMENT = "changeTaskFragment";
    public static final String ARGS_STATE_FROM_PAGER_ACTIVITY = "STATE_FROM_PAGER_ACTIVITY";
    public static final String ARGS_USER_FROM_PAGER_ACTIVITY = "USER_FROM_PAGER_ACTIVITY";

    private static final String TAG_USER_LIST = "userListFragment";


    public static final int REQUEST_CODE_TASK_DETAIL_FRAGMENT = 10;
    public static final int REQUEST_CODE_CHANGE_TASK_FRAGMENT = 20;
    public static final int REQUEST_CODE_USER_LIST = 50;
    public static final int RESULT_CODE_DELETE_TASK_SECOND = 100;
    public static final String EXTRA_TASK_DELETE_SECOND = "com.example.mytaskmanager.EXTRA_TASK_DELETE_SECOND";


    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private FloatingActionButton mAddTask;
    private ImageView mEmptyImage, mDeleteTask, mEditTask;
    private TextView mEmptyText;

    private Task mTask = new Task();
    private User mUser = new User();
    private State mState;
    private List<Task> mTaskList = new ArrayList<>();
    private List<Task> mTaskListFinal = new ArrayList<>();

    private List<TaskUser> mTaskUserList = new ArrayList<>();
    private TaskDBRepository mTaskDBRepository;
    private TaskUserDBRepository mTaskUserDBRepository;


    public TaskListFragment() {
        // Required empty public constructor
    }

    public static TaskListFragment newInstance(State state, User user) {
        TaskListFragment fragment = new TaskListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_STATE_FROM_PAGER_ACTIVITY, state);
        args.putSerializable(ARGS_USER_FROM_PAGER_ACTIVITY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);

        mState = (State) getArguments().getSerializable(ARGS_STATE_FROM_PAGER_ACTIVITY);

        mUser = (User) getArguments().getSerializable(ARGS_USER_FROM_PAGER_ACTIVITY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_list, container, false);

        findViews(view);
        initViews();
        setListeners();


        return view;
    }

    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mTaskDBRepository = TaskDBRepository.getInstance(getActivity());
        mTaskUserDBRepository = TaskUserDBRepository.getInstance(getActivity());
        findUserTask();
        mTaskAdapter = new TaskAdapter(mTaskListFinal);
        mRecyclerView.setAdapter(mTaskAdapter);
        if (mTaskListFinal != null)
            updateUI(mState);
    }

    public void findUserTask() {
        mTaskListFinal.clear();
        mTaskList = mTaskDBRepository.getTasksList(mState);
        //mTaskListFinal = mTaskDBRepository.getTasksList(mState);
        mTaskUserList = mTaskUserDBRepository.getUserTask(mUser.getId());

        Log.i("leila", mUser.getUserName());

        for (Task task : mTaskList) {
            for (TaskUser taskUser : mTaskUserList) {
                if (task.getTaskID().equals(taskUser.getTaskID())) {
                    mTaskListFinal.add(task);
                }
            }
        }


    }

    public void updateUI(State state) {
        if (mTaskDBRepository != null) {
            findUserTask();
            // mTaskList = mTaskDBRepository.getTasksList(state);
            if (mTaskListFinal != null || mTaskListFinal.size() != 0) {
                mEmptyImage.setVisibility(View.GONE);
                mEmptyText.setVisibility(View.GONE);
                if (isAdded()) {
                    if (mTaskAdapter != null) {
                        mTaskAdapter.setTasks(mTaskListFinal);
                        mTaskAdapter.notifyDataSetChanged();
                    } else {
                        mTaskAdapter = new TaskAdapter(mTaskListFinal);
                        mRecyclerView.setAdapter(mTaskAdapter);
                    }

                }
                if (mTaskListFinal == null || mTaskListFinal.size() == 0) {
                    mEmptyImage.setVisibility(View.VISIBLE);
                    mEmptyText.setVisibility(View.VISIBLE);
                }
            }
        }
    }


    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.task_list_recyclerView);
        mAddTask = view.findViewById(R.id.add_task_floating);
        mEmptyImage = view.findViewById(R.id.empty_task_image_view);
        mEmptyText = view.findViewById(R.id.empty_image_text);

    }

    private void setListeners() {
        mAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TaskDetailFragment taskDetailFragment =
                        TaskDetailFragment.newInstance(mTask, mState);

                taskDetailFragment.setTargetFragment(
                        TaskListFragment.this, REQUEST_CODE_TASK_DETAIL_FRAGMENT);

                taskDetailFragment.show(getFragmentManager(), TASK_DETAIL_FRAGMENT);

            }
        });

    }

    private class TaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle, mTextViewDate, mTextViewIcon;
        private Task mTask;

        public TaskHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.task_item_title);
            mTextViewDate = itemView.findViewById(R.id.task_item_date);
            mTextViewIcon = itemView.findViewById(R.id.task_item_image_view);
            mDeleteTask = itemView.findViewById(R.id.task_item_remove);
            mEditTask = itemView.findViewById(R.id.task_item_edit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ChangeTaskFragment changeTaskFragment = ChangeTaskFragment.newInstance(mTask);

                    changeTaskFragment.setTargetFragment(
                            TaskListFragment.this, REQUEST_CODE_CHANGE_TASK_FRAGMENT);

                    changeTaskFragment.show(getFragmentManager(), CHANGE_TASK_FRAGMENT);

                }
            });

            mDeleteTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                            .setTitle("Do You Want to Delete Task?!")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    State state = mTask.getTaskState();
                                    mTaskDBRepository.removeSingleTask(mTask);
                                    updateUI(state);
                                }
                            })
                            .setNegativeButton("No", null);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            mEditTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ChangeTaskFragment changeTaskFragment = ChangeTaskFragment.newInstance(mTask);

                    changeTaskFragment.setTargetFragment(
                            TaskListFragment.this, REQUEST_CODE_CHANGE_TASK_FRAGMENT);

                    changeTaskFragment.show(getFragmentManager(), CHANGE_TASK_FRAGMENT);
                }
            });
        }

        public void bindTask(Task task) {

            mTask = task;
            mTextViewTitle.setText(task.getTaskTitle());
            mTextViewDate.setText(task.getJustDate() + " " + task.getJustTime());
            if (task.getTaskTitle().length() != 0)
                mTextViewIcon.setText(task.getTaskTitle().charAt(0) + "");

        }
    }

    private class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

        private List<Task> mTasks;
        private List<Task> mTasksSearch;

        public List<Task> getTasks() {
            return mTasks;
        }

        public void setTasks(List<Task> tasks) {
            mTasks = tasks;
        }

        public TaskAdapter(List<Task> tasks) {
            mTasks = tasks;
        }

        @NonNull
        @Override
        public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.task_row_list, parent, false);

            TaskHolder taskHolder = new TaskHolder(view);
            return taskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
            Task task = mTasks.get(position);
            holder.bindTask(task);

        }

        @Override
        public int getItemCount() {
            if (mTasks != null)
                return mTasks.size();
            return 0;
        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data == null)
            return;

        if (requestCode == REQUEST_CODE_TASK_DETAIL_FRAGMENT) {

            Task task =
                    (Task) data.getSerializableExtra(TaskDetailFragment.EXTRA_TASK);

            TaskUser taskUser = new TaskUser(task.getTaskID(), mUser.getId());

            Log.i("info",mUser.getId().toString());
            Log.i("info",task.getTaskID().toString());


            mTaskDBRepository.insertTask(task);
            taskUser.setId(mUser.getId());
            mTaskUserDBRepository.insertTask(taskUser);
            updateUI(mState);
        }

        if (requestCode == REQUEST_CODE_CHANGE_TASK_FRAGMENT) {
            Task task;
            switch (resultCode) {
                case ChangeTaskFragment.RESULT_CODE_EDIT_TASK:
                    task = (Task) data.getSerializableExtra(ChangeTaskFragment.EXTRA_TASK_CHANGE);
                    mTaskDBRepository.updateTask(task);
                    updateUI(task.getTaskState());

                    break;
                case ChangeTaskFragment.RESULT_CODE_DELETE_TASK:
                    task = (Task) data.getSerializableExtra(ChangeTaskFragment.EXTRA_TASK_DELETE);
                    State state = task.getTaskState();
                    mTaskDBRepository.removeSingleTask(task);
                    updateUI(state);
                    break;
                default:
                    break;
            }

        }

    }

    public void updateEditUI() {
        if (mTaskAdapter != null)
            mTaskAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        updateEditUI();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateEditUI();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull final MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        MenuItem admin = menu.findItem(R.id.menu_admin);

        if (mUser.getUserName().equals("admin"))
            admin.setVisible(true);

        admin.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                /*
                UserListFragment userListFragment = UserListFragment.newInstance();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();

// Replace whatever is in the fragment_container view with this fragment,
// and add the transaction to the back stack so the user can navigate back
                transaction.replace(R.id.fragment_container, userListFragment);
                transaction.addToBackStack(null);

// Commit the transaction
                transaction.commit();

                 */

                Intent intent = new Intent(getActivity(), UserListActivity.class);
                startActivity(intent);


                return true;
            }

        });


        MenuItem logout = menu.findItem(R.id.menu_logout);
        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Do You Want To Exit?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                getActivity().startActivity(MainActivity.newIntent(getActivity()));

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

        MenuItem deleteAll = menu.findItem(R.id.menu_remove_all);
        deleteAll.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                        .setTitle("Do You Want To Delete All Tasks?")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTaskDBRepository.removeTasks();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, null);

                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

    }


}