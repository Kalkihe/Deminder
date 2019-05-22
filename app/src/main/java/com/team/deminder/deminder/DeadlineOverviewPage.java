package com.team.deminder.deminder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.DeadlineLayoutWidget;

import java.util.ArrayList;
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
    //private Menu menu;
    private Toolbar mTopToolbar;

    //wird beim Start des Programms aufgerufen
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadline_overview_page);
        storageManager = new StorageManager(this);
        buildLayout();

    }
    // Menu in toolbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // this.menu = menu;
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.sort_deadlines_alphabetically) {
            Toast.makeText(DeadlineOverviewPage.this, "Sorted alphabetically", Toast.LENGTH_LONG).show();
            return true;
        }
        if(id == R.id.sort_deadlines_by_date) {
            Toast.makeText(DeadlineOverviewPage.this, "Sorted by date", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        deadlineList = storageManager.loadDeadlines();
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
