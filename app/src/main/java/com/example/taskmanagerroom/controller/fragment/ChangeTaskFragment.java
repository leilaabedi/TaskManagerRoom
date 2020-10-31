package com.example.taskmanagerroom.controller.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;


import com.example.taskmanagerroom.R;
import com.example.taskmanagerroom.model.State;
import com.example.taskmanagerroom.model.Task;
import com.example.taskmanagerroom.repository.IRepository;
import com.example.taskmanagerroom.repository.TaskDBRepository;
import com.example.taskmanagerroom.utils.PictureUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class ChangeTaskFragment extends DialogFragment {
    public static final String AUTHORITY = "com.example.taskmanagerroom.fileProvider";
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 2;
    public static final String TAG = "CDF";
    private Button mButtonReport;


    private ImageView mImageViewPhoto;
    private ImageButton mImageButtonTakePicture;
    private File mPhotoFile;
    private IRepository mRepository;


    public static final String ARGS_TASK_CHANGE = "ARGS_TASK_CHANGE";

    public static final int REQUEST_CODE_DATE_PICKER_CHANGE_TASK = 30;
    public static final int REQUEST_CODE_TIME_PICKER_CHANGE_TASK = 40;

    public static final String TAG_DATE_PICKER_FRAGMENT_CHANGE_TASK = "TAG_DATE_PICKER_FRAGMENT";
    public static final String TAG_TIME_PICKER_FRAGMENT_CHANGE_TASK = "TAG_TIME_PICKER_FRAGMENT";

    public static final String EXTRA_TASK_CHANGE = "com.example.mytaskmanager.EXTRA_TASK_CHANGE";
    public static final String EXTRA_TASK_DELETE = "com.example.mytaskmanager.EXTRA_TASK_CHANGE_DELETE";

    public static final int RESULT_CODE_EDIT_TASK = 50;
    public static final int RESULT_CODE_DELETE_TASK = 60;

    private EditText mTaskTitle, mTaskDescription;
    private Button mTaskDate, mTaskTime;
    private RadioButton mRadioButtonToDo, mRadioButtonDone, mRadioButtonDoing;
    private RadioGroup mRadioGroupState;

    private Task mTask;


    public ChangeTaskFragment() {
        // Required empty public constructor
    }

    public static ChangeTaskFragment newInstance(Task task) {
        ChangeTaskFragment fragment = new ChangeTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_TASK_CHANGE, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTask = (Task) getArguments().getSerializable(ARGS_TASK_CHANGE);

        mRepository = TaskDBRepository.getInstance(getActivity());
        mPhotoFile = mRepository.getPhotoFile(mTask);

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View view = inflater.inflate(R.layout.fragment_change_task, null);

        findViews(view);
        initViews();
        updatePhotoView();

        setListeners();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())

                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        sendResultForEdit(mTask);
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setView(view);

        return builder.create();
    }

    private void initViews() {
        mTaskTitle.setText(mTask.getTaskTitle());
        mTaskDescription.setText(mTask.getTaskDescription());
        mTaskDate.setText(mTask.getJustDate());
        mTaskTime.setText(mTask.getJustTime());
    }

    private void findViews(View view) {
        mTaskTitle = view.findViewById(R.id.change_task_title);
        mTaskDescription = view.findViewById(R.id.change_task_description);
        mTaskDate = view.findViewById(R.id.change_task_date);
        mTaskTime = view.findViewById(R.id.change_task_time);
        mRadioGroupState = view.findViewById(R.id.radioGroup_taskState);
        mRadioButtonToDo = view.findViewById(R.id.radioButton_todo);
        mRadioButtonDoing = view.findViewById(R.id.radioButton_doing);
        mRadioButtonDone = view.findViewById(R.id.radioButton_done);
        mImageViewPhoto = view.findViewById(R.id.imgview_photo);
        mImageButtonTakePicture = view.findViewById(R.id.imgbtn_take_picture);
        mButtonReport = view.findViewById(R.id.send_report);
    }

    private void setListeners() {
        mTaskTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTask.setTaskTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTaskDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mTask.setTaskDescription(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerFragment datePickerFragment =
                        DatePickerFragment.newInstance(mTask.getTaskDate());

                datePickerFragment.setTargetFragment(
                        ChangeTaskFragment.this, REQUEST_CODE_DATE_PICKER_CHANGE_TASK);

                datePickerFragment.show(
                        getActivity().getSupportFragmentManager(), TAG_DATE_PICKER_FRAGMENT_CHANGE_TASK);


            }
        });

        mTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TimePickerFragment timePickerFragment =
                        TimePickerFragment.newInstance(mTask.getTaskDate());

                timePickerFragment.setTargetFragment(
                        ChangeTaskFragment.this, REQUEST_CODE_TIME_PICKER_CHANGE_TASK);

                timePickerFragment.show(
                        getActivity().getSupportFragmentManager(), TAG_TIME_PICKER_FRAGMENT_CHANGE_TASK);
            }
        });

        mRadioGroupState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                State state = mTask.getTaskState();

                if (checkedId == mRadioButtonToDo.getId()) {
                    mRadioButtonToDo.setChecked(true);

                    mTask.setTaskState(State.TODO);
                    TaskDBRepository.getInstance(getActivity()).updateTask(mTask);

                } else if (checkedId == mRadioButtonDoing.getId()) {
                    mRadioButtonDoing.setChecked(true);
                    mTask.setTaskState(State.DOING);
                    TaskDBRepository.getInstance(getActivity()).updateTask(mTask);

                } else if (checkedId == mRadioButtonDone.getId()) {
                    mRadioButtonDone.setChecked(true);
                    mTask.setTaskState(State.DONE);
                    TaskDBRepository.getInstance(getActivity()).updateTask(mTask);

                }

                updateEditUI(state);


            }
        });

        mImageButtonTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("leila","before");
                takePictureIntent();
            }
        });


        mButtonReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareReportIntent();
            }
        });

    }


    private String getReport() {
        String title = mTask.getTaskTitle();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:SS");
        String dateString = simpleDateFormat.format(mTask.getTaskDate());

        String taskDescription = mTask.getTaskDescription();

        String report = title+"      "+dateString+"        "+taskDescription;
        return report;
    }

    private void shareReportIntent() {
        Intent sendIntent = new Intent(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, getReport());
        sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Task Message");
        sendIntent.setType("text/plain");

        Intent shareIntent =
                Intent.createChooser(sendIntent, getString(R.string.send_report));

        //we prevent app from crash if the intent has no destination.
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivity(shareIntent);
    }





    private void takePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        try {
            if (mPhotoFile != null && takePictureIntent
                    .resolveActivity(getActivity().getPackageManager()) != null) {

                // file:///data/data/com.example.ci/files/234234234234.jpg
                Uri photoUri = generateUriForPhotoFile();

                grantWriteUriToAllResolvedActivities(takePictureIntent, photoUri);

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE);
            }
        } catch (ActivityNotFoundException e) {
            Log.e(TAG, e.getMessage(), e);
        }
    }


    private void grantWriteUriToAllResolvedActivities(Intent takePictureIntent, Uri photoUri) {
        List<ResolveInfo> activities = getActivity().getPackageManager()
                .queryIntentActivities(
                        takePictureIntent,
                        PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo activity: activities) {
            getActivity().grantUriPermission(
                    activity.activityInfo.packageName,
                    photoUri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
    }

    private Uri generateUriForPhotoFile() {
        return FileProvider.getUriForFile(
                getContext(),
                AUTHORITY,
                mPhotoFile);
    }


    private void updatePhotoView() {
        if (mPhotoFile == null || !mPhotoFile.exists())
            return;

        //this has a better memory management.
        Bitmap bitmap = PictureUtils.getScaledBitmap(mPhotoFile.getAbsolutePath(), getActivity());
        mImageViewPhoto.setImageBitmap(bitmap);
    }



    private void updateEditUI(State state) {
        if (getTargetFragment() instanceof TaskListFragment) {
            ((TaskListFragment) getTargetFragment()).updateUI(state);
        }
    }


    private void updateTaskDate(Date userSelectedDate) {

        mTask.setTaskDate(userSelectedDate);
        mTaskDate.setText(mTask.getJustDate());
    }

    private void updateTaskTime(Date userSelectedTime) {

        mTask.setTaskTime(userSelectedTime);
        mTaskTime.setText(mTask.getJustTime());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK || data == null)
            return;

        if (requestCode == REQUEST_CODE_DATE_PICKER_CHANGE_TASK) {
            Date userSelectedDate =
                    (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_USER_SELECTED_DATE);

            updateTaskDate(userSelectedDate);
        }

        if (requestCode == REQUEST_CODE_TIME_PICKER_CHANGE_TASK) {
            Date userSelectedTime =
                    (Date) data.getSerializableExtra(TimePickerFragment.EXTRA_USER_SELECTED_TIME);

            updateTaskTime(userSelectedTime);
        }

        if (requestCode == REQUEST_CODE_IMAGE_CAPTURE) {
            Uri photoUri = generateUriForPhotoFile();
            getActivity().revokeUriPermission(photoUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            updatePhotoView();
        }


    }

    private void sendResultForEdit(Task task) {

        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = RESULT_CODE_EDIT_TASK;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_CHANGE, task);

        fragment.onActivityResult(requestCode, resultCode, intent);
    }

    private void sendResultForDelete(Task task) {
        Fragment fragment = getTargetFragment();
        int requestCode = getTargetRequestCode();
        int resultCode = RESULT_CODE_DELETE_TASK;

        Intent intent = new Intent();
        intent.putExtra(EXTRA_TASK_DELETE, task);

        fragment.onActivityResult(requestCode, resultCode, intent);
    }
}