package com.team.deminder.deminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.DeadlineLayoutWidget;
import com.team.deminder.deminder.customLayoutComponents.SortDialogFragment;
import com.team.deminder.deminder.customLayoutComponents.SortDialogFragment.AlertPositiveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;

public class DeadlineOverviewPage extends AppCompatActivity implements AlertPositiveListener {
    private HashMap<Integer, DeadlineLayoutWidget> mapDeadlineLayout = new HashMap<>();
    private ArrayList<Deadline> deadlineList = new ArrayList<>();
    private StorageManager storageManager;
    private LinearLayout deadlineListLayout;
    private Toolbar mTopToolbar;
    private int deadlineID = 0;

    //wird beim Start des Programms aufgerufen
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadline_overview_page);
        storageManager = new StorageManager(this);
        deadlineList = storageManager.loadDeadlines();
        buildLayout();
    }
    // Menu in toolbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here
        int id = item.getItemId();

        if (id == R.id.sort_deadlines_by_date) {
            FragmentManager manager = getSupportFragmentManager();
            SortDialogFragment alert = new SortDialogFragment();
            Bundle b = new Bundle();
            b.putInt("position", 0);
            alert.setArguments(b);
            alert.show(manager, "alert_dialog_radio");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPositiveClick(int position) {

        switch(position){
            case 0:
                Collections.sort(deadlineList, ((o1, o2) -> o1.getDeadlineName().compareTo(o2.getDeadlineName())));
                break;
            case 1:
                Collections.sort(deadlineList, ((o1, o2) -> o1.getDeadlineDate().compareTo(o2.getDeadlineDate())));
                break;
        }
        deadlineListLayout.removeAllViews();
        mapDeadlineLayout.clear();
        buildLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultIntent) {
        if (resultCode == Activity.RESULT_OK) {
            final Deadline deadline = (Deadline) resultIntent.getSerializableExtra("deadline");
            if (resultIntent.getBooleanExtra("deleted", false)) {
                storageManager.deleteDeadline(deadline);
                deadlineList.remove(deadline);
                deadlineListLayout.removeView(mapDeadlineLayout.get(requestCode).getLayout());
                mapDeadlineLayout.remove(requestCode);
            } else {
                storageManager.saveDeadline(deadline);
                if (!resultIntent.getBooleanExtra("isNewDeadline", false)) {
                    deadlineList.remove(deadline);
                    deadlineListLayout.removeView(mapDeadlineLayout.get(requestCode).getLayout());
                    mapDeadlineLayout.remove(requestCode);
                }
                addNewDeadline(deadline);
            }
        }
    }

    private void buildLayout() {
        deadlineListLayout = this.findViewById(R.id.deadlineList);
        mTopToolbar = findViewById(R.id.my_toolbar);
        mTopToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mTopToolbar);

        FloatingActionButton newDeadlineButton = findViewById(R.id.newDeadlineButton);
        newDeadlineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeadlineOverviewPage.this, ManageDeadlinePage.class);
                startActivityForResult(intent, 1);
            }
        });

        FloatingActionButton importButton = findViewById(R.id.importButton);
        importButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Import Funktion
            }
        });

        for (final Deadline deadline : deadlineList) {
            addNewDeadline(deadline);
        }

    }

    private void addNewDeadline(Deadline deadline) {
        DeadlineLayoutWidget deadlineLayoutWidget = new DeadlineLayoutWidget(deadline, this, this.deadlineID,this);
        LinearLayout linearLayout = deadlineLayoutWidget.getLayout();
        mapDeadlineLayout.put(deadlineID, deadlineLayoutWidget);
        deadlineID++;
        deadlineListLayout.addView(linearLayout);
    }
}
