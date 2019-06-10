package com.team.deminder.deminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.Containers.Subtask;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.DeadlineLayoutWidget;
import com.team.deminder.deminder.customLayoutComponents.SortDialogFragment;
import com.team.deminder.deminder.customLayoutComponents.SortDialogFragment.AlertPositiveListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class DeadlineOverviewPage extends AppCompatActivity implements AlertPositiveListener {
    private ArrayList<Deadline> deadlineList = new ArrayList<>();
    Handler h = new Handler();
    private StorageManager storageManager;
    private LinearLayout deadlineListLayout;
    private Toolbar mTopToolbar;
    private int deadlineID = 0;
    private int sortedBy = 1;

    private String importString;

    private final String regexToMatchInput = "====-DEADLINE-====\\nTask:.*\\nDeadline:([0-2][0-9]|(3)[0-1])(\\-)(((0)[0-9])|((1)[0-2]))(\\-)\\d{4}\\nNotes:.*\\n(Subtasks:\\n(.*(\\[X\\]|\\[ \\]).*\\n)+)?====-DEADLINE-====";

    //wird beim Start des Programms aufgerufen
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.deadline_overview_page);
        storageManager = new StorageManager(this);
        deadlineList = storageManager.loadDeadlines();
        sortDeadlines();
        buildLayout();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if (type.equals("text/plain")) {
                String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
                if (sharedText != null) {
                    View contextView = findViewById(R.id.deadlineOverviewPage);
                    if (sharedText.matches(this.regexToMatchInput)) {
                        importDeadline(sharedText);
                        Snackbar.make(contextView, "Importierte Deadline angelegt", Snackbar.LENGTH_SHORT)
                                .show();
                    } else {
                        Snackbar.make(contextView, "Keine gültige Deadline", Snackbar.LENGTH_SHORT)
                                .show();
                    }
                }
            }
        }
        else {

        }

        //Update deadlines every 5minutes
        h.postDelayed(new Runnable(){
            public void run(){
                h.postDelayed(this, 300000);
                buildLayout();
            }
        }, 300000);
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
                showImportDialog();
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

    private void importDeadline(String sharedText)
    {
        ArrayList<Subtask> subtasks = new ArrayList<Subtask>();
        String[] currentParts;
        String[] parts = sharedText.split("\\n");
        String deadlineName = "";
        String deadlineNotes = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar deadlineDate = Calendar.getInstance();
        for (int index = 1; index < parts.length -1; index++) {
            if (index >= 1 && index <= 3) {
                currentParts = parts[index].split(":");
                if (index == 1) {
                    deadlineName = currentParts[1];
                }
                if (index == 2) {
                    try {
                        deadlineDate.setTime(dateFormat.parse(currentParts[1]));
                    }
                    catch (Exception ex) {

                    }
                }
                if (index == 3) {
                    deadlineNotes = currentParts[1];
                }
            }
            if (index > 4) {
                boolean isComplete;
                currentParts = parts[index].split("]",2);
                isComplete = currentParts[0].contains("X");
                String subtaskName = currentParts[1];
                subtasks.add(new Subtask(subtaskName, isComplete));
            }
        }
        Deadline deadline = new Deadline(deadlineName, deadlineDate, deadlineNotes, subtasks);
        storageManager.saveDeadline(deadline);
        addNewDeadline(deadline);
    }

    public void showImportDialog() {
        this.importString = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Paste exported deadline here:");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setSingleLine(false);
        input.setVerticalScrollBarEnabled(true);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                importString = input.getText().toString();
                View contextView = findViewById(R.id.deadlineOverviewPage);
                if (importString.matches(regexToMatchInput)) {
                    importDeadline(importString);
                    Snackbar.make(contextView, "Importierte Deadline angelegt", Snackbar.LENGTH_SHORT)
                            .show();
                }
                else {
                    Snackbar.make(contextView, "Keine gültige Deadline", Snackbar.LENGTH_SHORT)
                            .show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}
