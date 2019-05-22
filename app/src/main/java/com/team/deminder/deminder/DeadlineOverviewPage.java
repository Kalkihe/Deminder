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
import android.widget.TextView;
import android.widget.Toast;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.DeadlineLayoutWidget;
import com.team.deminder.deminder.customLayoutComponents.SortDialogFragment;
import com.team.deminder.deminder.customLayoutComponents.SortDialogFragment.AlertPositiveListener;

import java.util.ArrayList;
import java.util.HashMap;

public class DeadlineOverviewPage extends AppCompatActivity implements AlertPositiveListener {
    private ArrayList<Deadline> deadlineList = new ArrayList<>();
    private SettingsPage settingsPage;
    private StorageManager storageManager;
    private ManageDeadlinePage manageDeadlinePage;
    private DeadlineLayoutWidget deadlineLayoutWidget;
    private LinearLayout deadlineListLayout;
    private HashMap<Integer, DeadlineLayoutWidget> mapDeadlineLayout = new HashMap<>();
    private int deadlineID = 0;
    private Toolbar mTopToolbar;
    private int position = 0; // Stores the selected item's position
    private Button btn;

    //wird beim Start des Programms aufgerufen
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadline_overview_page);
        deadlineListLayout = this.findViewById(R.id.deadlineList);
        storageManager = new StorageManager(this);
        deadlineList = storageManager.loadDeadlines();
        mTopToolbar = findViewById(R.id.my_toolbar);
        mTopToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mTopToolbar);
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

            /** Getting the fragment manager */
            FragmentManager manager = getSupportFragmentManager();

            /** Instantiating the DialogFragment class */
            SortDialogFragment alert = new SortDialogFragment();

            /** Creating a bundle object to store the selected item's index */
            Bundle b = new Bundle();

            /** Storing the selected item's index in the bundle object */
            b.putInt("position", position);

            /** Setting the bundle object to the dialog fragment object */
            alert.setArguments(b);

            /** Creating the dialog fragment object, which will in turn open the alert dialog window */
            alert.show(manager, "alert_dialog_radio");

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void buildLayout() {
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
            DeadlineLayoutWidget deadlineLayoutWidget = new DeadlineLayoutWidget(deadline, this);
            LinearLayout linearLayout = deadlineLayoutWidget.getLayout();
            final int DeadlineID = DeadlineOverviewPage.this.deadlineID;
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DeadlineOverviewPage.this, ManageDeadlinePage.class);
                    intent.putExtra("deadline", deadline);
                    startActivityForResult(intent, DeadlineID);
                }
            });
            mapDeadlineLayout.put(deadlineID, deadlineLayoutWidget);
            deadlineID++;
            deadlineListLayout.addView(linearLayout);
        }

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
                DeadlineLayoutWidget deadlineLayoutWidget = new DeadlineLayoutWidget(deadline, this);
                LinearLayout linearLayout = deadlineLayoutWidget.getLayout();
                final int deadlineID = DeadlineOverviewPage.this.deadlineID;
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DeadlineOverviewPage.this, ManageDeadlinePage.class);
                        intent.putExtra("deadline", deadline);
                        startActivityForResult(intent, deadlineID);
                    }
                });
                mapDeadlineLayout.put(deadlineID, deadlineLayoutWidget);
                DeadlineOverviewPage.this.deadlineID++;
                deadlineListLayout.addView(linearLayout);
            }
        }
        if (resultCode == Activity.RESULT_CANCELED) {
            // TODO or do nothing if the user canceled the creation of a new deadline
        }
    }


    @Override
    public void onPositiveClick(int position) {
        this.position = position;

        /** Getting the reference of the textview from the main layout */
        TextView tv = findViewById(R.id.tv_android);

        /** Setting the selected android version in the textview */
        tv.setText("Your Choice : " + SortDialogFragment.sortDeadlinesOptions[this.position]);
    }
}
