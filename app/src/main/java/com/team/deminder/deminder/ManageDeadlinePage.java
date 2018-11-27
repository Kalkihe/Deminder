package com.team.deminder.deminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.Containers.Subtask;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.SubtaskLayoutWidget;

import java.io.Serializable;
import java.util.ArrayList;

public class ManageDeadlinePage extends AppCompatActivity {
    private Deadline deadline;
    private StorageManager storageManager;
    private SubtaskLayoutWidget subtaskLayoutWidget;
    private Intent intent;

    // Is called when a new ManageDeadlinePage is called
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_deadline_page);
        intent = getIntent();
        deadline = (Deadline) intent.getSerializableExtra("deadline");
    }

}
