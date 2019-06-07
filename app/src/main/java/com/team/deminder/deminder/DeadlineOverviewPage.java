package com.team.deminder.deminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
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
    private ArrayList<Deadline> deadlineList = new ArrayList<>();
    private StorageManager storageManager;
    private LinearLayout deadlineListLayout;
    private Toolbar mTopToolbar;
    private int deadlineID = 0;
    private int sortedBy = 1;

    //wird beim Start des Programms aufgerufen
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadline_overview_page);
        storageManager = new StorageManager(this);
        deadlineList = storageManager.loadDeadlines();
        sortDeadlines();
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
            b.putInt("position", sortedBy);
            alert.setArguments(b);
            alert.show(manager, "alert_dialog_radio");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPositiveClick(int position) {
        sortedBy = position;
        sortDeadlines();
        buildLayout();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent resultIntent) {
        if (resultCode == Activity.RESULT_OK) {
            final Deadline deadline = (Deadline) resultIntent.getSerializableExtra("deadline");
            final Boolean deleted = resultIntent.getBooleanExtra("deleted", false);
            if (deleted) {
                deadlineList.remove(deadline);
                sortDeadlines();
                storageManager.deleteDeadline(deadline);
            } else {
                if (!resultIntent.getBooleanExtra("isNewDeadline", false)) {
                    deadlineList.remove(deadline);
                }
                deadlineList.add(deadline);
                sortDeadlines();
                storageManager.saveDeadline(deadline);
            }
            buildLayout();
        }
    }

    private void buildLayout() {
        deadlineListLayout = this.findViewById(R.id.deadlineList);
        mTopToolbar = findViewById(R.id.my_toolbar);
        mTopToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mTopToolbar);

        deadlineListLayout.removeAllViews();

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
        updateWidget();
    }

    private void addNewDeadline(Deadline deadline) {
        DeadlineLayoutWidget deadlineLayoutWidget = new DeadlineLayoutWidget(deadline, this, this.deadlineID,this);
        LinearLayout linearLayout = deadlineLayoutWidget.getLayout();
        deadlineID++;
        deadlineListLayout.addView(linearLayout);
    }

    private void sortDeadlines(){
        switch(sortedBy){
            case 0:
                Collections.sort(deadlineList, ((o1, o2) -> o1.getDeadlineName().compareTo(o2.getDeadlineName())));
                break;
            case 1:
                Collections.sort(deadlineList, ((o1, o2) -> o1.getDeadlineDate().compareTo(o2.getDeadlineDate())));
                break;
        }
    }

    private void updateWidget(){
        Intent intent = new Intent(this, DeminderWidget.class);
        intent.setAction("Deminder.UPDATE_DEADLINE");
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(),DeminderWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);

        for (int i = 1; i < deadlineList.size()+1  ; i++) {
            if (i <= 4){
                intent.putExtra("deadline" + i,deadlineList.get(i-1));
            } else {
                break;
            }
        }
        sendBroadcast(intent);
    }

}
