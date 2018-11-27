package com.team.deminder.deminder;

import android.content.Intent;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.DeadlineLayoutWidget;
import com.team.deminder.deminder.Containers.Deadline;
import java.util.ArrayList;
import java.util.Date;

public class DeadlineOverviewPage extends AppCompatActivity {
    private ArrayList deadlineList;
    private SettingsPage settingsPage;
    private StorageManager storageManager;
    private ManageDeadlinePage manageDeadlinePage;
    private DeadlineLayoutWidget deadlineLayoutWidget;


    //wird beim Start des Programms aufgerufen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadline_overview_page);
        buildLayout();
        }

    private void buildLayout(){
        Button openManagePageButton = findViewById(R.id.openManagePageButton);
        openManagePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Deadline testDeadline = new Deadline("Test",new Date(),false,"notes",new ArrayList());
                Intent intent = new Intent(DeadlineOverviewPage.this, ManageDeadlinePage.class);
                intent.putExtra("deadline",testDeadline);
                startActivity(intent);
            }
        });

        Button createStorageManagerButton = findViewById(R.id.createStorageManagerButton);
        createStorageManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageManager storageManager = new StorageManager();
            }
        });
    }
}
