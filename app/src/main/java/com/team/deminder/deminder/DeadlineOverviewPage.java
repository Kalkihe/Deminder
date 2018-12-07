package com.team.deminder.deminder;

import android.app.Activity;
import android.content.Intent;
import android.icu.util.RangeValueIterator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.DeadlineLayoutWidget;
import com.team.deminder.deminder.Containers.Deadline;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class DeadlineOverviewPage extends AppCompatActivity {
    private ArrayList<Deadline> deadlineList = new ArrayList<>();
    private SettingsPage settingsPage;
    private StorageManager storageManager;
    private ManageDeadlinePage manageDeadlinePage;
    private DeadlineLayoutWidget deadlineLayoutWidget;
    private LinearLayout deadlineListLayout;
    private HashMap<Integer, DeadlineLayoutWidget> mapDeadlineLayout = new HashMap<>();
    private int deadlineID = 0;


    //wird beim Start des Programms aufgerufen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadline_overview_page);
        deadlineListLayout = this.findViewById(R.id.deadlineList);
        storageManager = new StorageManager(this);
        deadlineList = storageManager.loadDeadlines();

        buildLayout();

        }

    private void buildLayout(){
        Button createStorageManagerButton = findViewById(R.id.createStorageManagerButton);
        createStorageManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageManager storageManager = new StorageManager(DeadlineOverviewPage.this);
            }
        });

        FloatingActionButton newDeadlineButton = findViewById(R.id.newDeadlineButton);
        newDeadlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeadlineOverviewPage.this, ManageDeadlinePage.class);
                startActivityForResult(intent,1);
            }
        });


      for(Deadline deadline: deadlineList){
          DeadlineLayoutWidget deadlineLayoutWidget = new DeadlineLayoutWidget(deadline,this);
          LinearLayout linearLayout = deadlineLayoutWidget.getLayout();
          final int DeadlineID = DeadlineOverviewPage.this.deadlineID;
          linearLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(DeadlineOverviewPage.this, ManageDeadlinePage.class);
                  intent.putExtra("deadline",deadline);
                  startActivityForResult(intent,DeadlineID);
              }
          });
          mapDeadlineLayout.put(deadlineID,deadlineLayoutWidget);
          deadlineID++;
          deadlineListLayout.addView(linearLayout);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultIntent) {
        if(resultCode == Activity.RESULT_OK){
            final Deadline deadline = (Deadline) resultIntent.getSerializableExtra("deadline");
            if (resultIntent.getBooleanExtra("deleted",false)) {
               storageManager.deleteDeadline(deadline);
               deadlineList.remove(deadline);
               deadlineListLayout.removeView(mapDeadlineLayout.get(requestCode).getLayout());
               mapDeadlineLayout.remove(requestCode);
            } else {
                storageManager.saveDeadline(deadline);
                if (!resultIntent.getBooleanExtra("isNewDeadline",false)){
                    deadlineList.remove(deadline);
                    deadlineListLayout.removeView(mapDeadlineLayout.get(requestCode).getLayout());
                    mapDeadlineLayout.remove(requestCode);
                }
                DeadlineLayoutWidget deadlineLayoutWidget = new DeadlineLayoutWidget(deadline, this);
                LinearLayout linearLayout = deadlineLayoutWidget.getLayout();
                final int deadlineID = DeadlineOverviewPage.this.deadlineID;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DeadlineOverviewPage.this, ManageDeadlinePage.class);
                        intent.putExtra("deadline",deadline);
                        startActivityForResult(intent,deadlineID);
                    }
                });
                mapDeadlineLayout.put(deadlineID,deadlineLayoutWidget);
                DeadlineOverviewPage.this.deadlineID++;
                deadlineListLayout.addView(linearLayout);
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            // TODO or do nothing if the user canceled the creation of a new deadline
        }
    }
}
