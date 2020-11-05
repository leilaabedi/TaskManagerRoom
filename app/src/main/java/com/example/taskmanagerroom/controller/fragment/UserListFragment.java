package com.example.taskmanagerroom.controller.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerroom.R;
import com.example.taskmanagerroom.controller.activity.MainActivity;
import com.example.taskmanagerroom.model.AdminReport;
import com.example.taskmanagerroom.model.Task;
import com.example.taskmanagerroom.model.User;
import com.example.taskmanagerroom.model.UserCount;
import com.example.taskmanagerroom.repository.TaskDBRepository;
import com.example.taskmanagerroom.repository.TaskUserDBRepository;
import com.example.taskmanagerroom.repository.UserDBRepository;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UserListFragment extends Fragment {

    public static final String TASK_DETAIL_FRAGMENT = "taskDetailFragment";
    public static final String CHANGE_TASK_FRAGMENT = "changeTaskFragment";
    public static final String ARGS_STATE_FROM_PAGER_ACTIVITY = "STATE_FROM_PAGER_ACTIVITY";
    public static final String ARGS_USER_FROM_PAGER_ACTIVITY = "USER_FROM_PAGER_ACTIVITY";


    public static final int REQUEST_CODE_TASK_DETAIL_FRAGMENT = 10;
    public static final int REQUEST_CODE_CHANGE_TASK_FRAGMENT = 20;
    public static final int RESULT_CODE_DELETE_TASK_SECOND = 100;
    public static final String EXTRA_TASK_DELETE_SECOND = "com.example.mytaskmanager.EXTRA_TASK_DELETE_SECOND";


    private RecyclerView mRecyclerView;
    private UserTaskAdapter mUserTaskAdapter;
    private FloatingActionButton mAddTask;
    private ImageView mEmptyImage, mDeleteTask, mEditTask;
    private TextView mEmptyText;

    private UserCount mUserCount = new UserCount();
    private User mUser = new User();


    private List<UserCount> mTaskListFinal = new ArrayList<>();

    //private List<Task> mTaskList = new ArrayList<>();
    private List<User> mUserList = new ArrayList<>();

    private List<AdminReport> mAdminReportList = new ArrayList<>();


    private UserDBRepository mUserDBRepository;
    private TaskUserDBRepository mTaskUserDBRepository;
    // private TaskDBRepository mTaskDBRepository;


    public UserListFragment() {
        // Required empty public constructor
    }

    public static UserListFragment newInstance() {
        UserListFragment fragment = new UserListFragment();
        Bundle args = new Bundle();
        //args.putSerializable(ARGS_STATE_FROM_PAGER_ACTIVITY, state);
        //args.putSerializable(ARGS_USER_FROM_PAGER_ACTIVITY, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setHasOptionsMenu(true);

        //mState = (State) getArguments().getSerializable(ARGS_STATE_FROM_PAGER_ACTIVITY);

        //mUser = (User) getArguments().getSerializable(ARGS_USER_FROM_PAGER_ACTIVITY);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        findViews(view);
        initViews();
        // setListeners();
        return view;
    }


    private void findViews(View view) {
        mRecyclerView = view.findViewById(R.id.user_list_recyclerView);
        mEmptyImage = view.findViewById(R.id.empty_user_image_view1);
        mEmptyText = view.findViewById(R.id.empty_user_image_text1);
    }


    private void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mUserDBRepository = UserDBRepository.getInstance(getActivity());
        mTaskUserDBRepository = TaskUserDBRepository.getInstance(getActivity());

        //   mTaskDBRepository=TaskDBRepository.getInstance(getActivity());
        findUserTask();
        updateUI(mAdminReportList);
    }

    public void findUserTask() {
        mAdminReportList.clear();

        mTaskListFinal = mTaskUserDBRepository.getUserCount();
        //  mTaskList = mTaskDBRepository.getTasks();
        mUserList = mUserDBRepository.getUsers();

        // Log.i("leila", mUser.getUserName());

        for (UserCount userCount : mTaskListFinal) {
            Log.i("user", userCount.getId().toString());
            Log.i("user", userCount.getCount().toString());
            for (User user : mUserList) {
                Log.i("user", user.getId().toString());
                Log.i("user", user.getUserName());
                Log.i("user", user.getDate()+"  hello  ");

                if (user.getId().equals(userCount.getId())) {


                    AdminReport temp = new AdminReport();
                    temp.setUserId(user.getId());
                    temp.setUsername(user.getUserName());
                    temp.setUserDate(user.getDate());
                   // Log.i("user", temp.getUserDate());
                    temp.setCount(userCount.getCount());

                    mAdminReportList.add(temp);
                }
            }
        }


    }


    private void updateUI(List<AdminReport> adminReports) {
        if (adminReports.size() != 0) {

            mEmptyImage.setVisibility(View.GONE);
            mEmptyText.setVisibility(View.GONE);

            if (mUserTaskAdapter == null) {
                mUserTaskAdapter = new UserTaskAdapter(adminReports);
                mRecyclerView.setAdapter(mUserTaskAdapter);
            } else {
                mUserTaskAdapter.setAdminReports(adminReports);
                mUserTaskAdapter.notifyDataSetChanged();
            }
        } else {
            mEmptyImage.setVisibility(View.VISIBLE);
            mEmptyText.setVisibility(View.VISIBLE);
        }
    }


    private class UserTaskHolder extends RecyclerView.ViewHolder {

        private TextView mTextViewTitle, mTextViewDate, mTextViewCount;
        private UserCount mUerCount;
        private AdminReport mAdminReport;

        public UserTaskHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewTitle = itemView.findViewById(R.id.user_item_title);
            mTextViewDate = itemView.findViewById(R.id.user_item_date);
            mTextViewCount = itemView.findViewById(R.id.user_task_count);
            // mDeleteTask = itemView.findViewById(R.id.task_item_remove);

           /*
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

            */
        }

        public void bindTask(AdminReport adminReport) {
//todo
            mAdminReport = adminReport;
            mTextViewTitle.setText(mAdminReport.getUsername());
            mTextViewDate.setText(mAdminReport.getUserDate());
            mTextViewCount.setText(mAdminReport.getCount().toString());


        }
    }

    private class UserTaskAdapter extends RecyclerView.Adapter<UserTaskHolder> {

        // private List<UserCount> mUserCounts;
        private List<AdminReport> mAdminReports;

        public List<AdminReport> getAdminReports() {
            return mAdminReports;
        }

        public void setAdminReports(List<AdminReport> adminReports) {
            mAdminReports = adminReports;
        }

        public UserTaskAdapter(List<AdminReport> adminReports) {
            mAdminReports = adminReports;
        }

        @NonNull
        @Override
        public UserTaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.user_row_list, parent, false);

            UserTaskHolder userTaskHolder = new UserTaskHolder(view);
            return userTaskHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserTaskHolder holder, int position) {
            AdminReport adminReport = mAdminReports.get(position);
            holder.bindTask(adminReport);
        }

        @Override
        public int getItemCount() {
            if (mAdminReports != null)
                return mAdminReports.size();
            return 0;
        }


    }

    /*
        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            if (data == null)
                return;

            if (requestCode == REQUEST_CODE_TASK_DETAIL_FRAGMENT) {

                Task task =
                        (Task) data.getSerializableExtra(TaskDetailFragment.EXTRA_TASK);

                TaskUser taskUser = new TaskUser(task.getTaskID(), mUser.getId());

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

    */
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull final MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);

        MenuItem admin = menu.findItem(R.id.menu_admin);

        //if (mUser.getUserName().equals("admin"))
        //  admin.setVisible(true);


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

    }


}
