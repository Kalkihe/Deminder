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
    private HashMap<Deadline, LinearLayout> mapDeadlineLayout = new HashMap<>();


    //wird beim Start des Programms aufgerufen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadline_overview_page);
        deadlineListLayout = this.findViewById(R.id.deadlineList);
        buildLayout();
        }

    private void buildLayout(){
        Button createStorageManagerButton = findViewById(R.id.createStorageManagerButton);
        createStorageManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageManager storageManager = new StorageManager();
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


      for(final Deadline deadline: deadlineList){
          String deadlineDate= deadline.getDeadlineDate().toString();
          DeadlineLayoutWidget deadlineLayoutWidget = new DeadlineLayoutWidget(deadline.getDeadlineName(),  deadlineDate, this);
            LinearLayout linearLayout =deadlineLayoutWidget.getLayout();
          linearLayout.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Intent intent = new Intent(DeadlineOverviewPage.this, ManageDeadlinePage.class);
                  intent.putExtra("deadline",deadline);
                  startActivityForResult(intent,1);
              }
          });
          mapDeadlineLayout.put(deadline,linearLayout);
          deadlineListLayout.addView(linearLayout);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultIntent) {
        if(resultCode == Activity.RESULT_OK){
            final Deadline deadline = (Deadline) resultIntent.getSerializableExtra("deadline");
            if (resultIntent.getBooleanExtra("deleted",false)) {
               deadlineList.remove(deadline);
               deadlineListLayout.removeView(mapDeadlineLayout.get(deadline));
            } else {
                String deadlineDate= deadline.getDeadlineDate().toString();
                if (resultIntent.getBooleanExtra("isNewDeadline",false)){
                    DeadlineLayoutWidget deadlineLayoutWidget = new DeadlineLayoutWidget(deadline.getDeadlineName(),  deadlineDate, this);
                    LinearLayout linearLayout =deadlineLayoutWidget.getLayout();
                    linearLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DeadlineOverviewPage.this, ManageDeadlinePage.class);
                            intent.putExtra("deadline",deadline);
                            startActivityForResult(intent,1);
                        }
                    });
                    mapDeadlineLayout.put(deadline,linearLayout);
                    deadlineListLayout.addView(linearLayout);
                } else {
                    // TODO Update existing deadline widget
                }
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            // TODO or do nothing if the user caceled the creation of a new deadline
        }
    }
}
