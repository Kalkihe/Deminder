package com.team.deminder.deminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.Containers.Subtask;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.DatePickerFragment;
import com.team.deminder.deminder.customLayoutComponents.SubtaskLayoutWidget;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

// This will soon be an fragment instead of an activity
public class ManageDeadlinePage extends AppCompatActivity {
    private Deadline deadline;
    private SubtaskLayoutWidget subtaskLayoutWidget;
    private Intent intent;
    private Boolean isNewDeadline;
    private TextView textTaskName;
    private TextView textDeadline;
    private CheckBox checkBoxRecurring;
    private TextView textNotes;
    private ImageButton buttonAddSubtask;
    private ImageButton buttonSave;
    private ImageButton buttonDelete;
    // Referenz zum Button für den Export
    private ImageButton buttonExport;
    private LinearLayout subtaskList;
    ArrayList<SubtaskLayoutWidget> subtaskLayoutWidgets;
    Calendar calendar;
    SimpleDateFormat dateFormat;

    // Is called when a new ManageDeadlinePage is called
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_deadline_page);
        isNewDeadline = true;
        subtaskLayoutWidgets = new ArrayList<>();

        intent = getIntent();
        deadline = (Deadline) intent.getSerializableExtra("deadline");
        subtaskLayoutWidget = new SubtaskLayoutWidget("ASD",false,this);
        initialiseLayoutComponents();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        if (deadline != null) {
            // Wenn eine deadline mitgeschickt wurde fülle alle componenten mit dessen Daten
            fillLayoutComponents();
            isNewDeadline = false;
            calendar = deadline.getDeadlineDate();
        } else {
            deadline = new Deadline();
            calendar = Calendar.getInstance();
            textDeadline.setText(dateFormat.format(calendar.getTime()));
        }

    }


    @SuppressLint("CutPasteId")
    private void initialiseLayoutComponents() {
        //TODO use findElementByViewID to bind all layout components to fields
        subtaskList = this.findViewById(R.id.subtaskList);
        textTaskName = this.findViewById(R.id.textTaskName);
        textDeadline = this.findViewById(R.id.textDeadline);
        checkBoxRecurring = this.findViewById(R.id.checkBoxRecurring);
        textNotes = this.findViewById(R.id.textNotes);
        buttonAddSubtask = this.findViewById(R.id.buttonAddSubtask);
        buttonDelete = this.findViewById(R.id.buttonDelete);
        buttonSave = this.findViewById(R.id.buttonSave);
        // Export Button deklarieren
        buttonExport = this.findViewById(R.id.buttonExport);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDeadline();
            }
        });
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDeadline();
            }
        });

        buttonExport.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) { exportDeadline(); }
        });

        textDeadline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        buttonAddSubtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SubtaskLayoutWidget subtaskLayoutWidget = new SubtaskLayoutWidget("",false,ManageDeadlinePage.this);
                final View subtaskLayoutWidgetObject = subtaskLayoutWidget.getLayout();
                subtaskList.addView(subtaskLayoutWidgetObject);
                subtaskLayoutWidgets.add(subtaskLayoutWidget);

                ImageButton buttonDelete = subtaskLayoutWidget.getDeleteButton();
                buttonDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        subtaskList.removeView(subtaskLayoutWidgetObject);
                        subtaskLayoutWidgets.remove(subtaskLayoutWidget);
                    }
                });
            }
        });
    }

    private void fillLayoutComponents() {
        //TODO fill in all the layou components with data from the existing deadline

        textTaskName.setText(deadline.getDeadlineName());
        textDeadline.setText(dateFormat.format(deadline.getDeadlineDate().getTime()));
        if (deadline.isReacurring()){
            checkBoxRecurring.setChecked(true);
        }
        textNotes.setText(deadline.getNotes());
        ArrayList<Subtask> subtasks = deadline.getSubtaskList();
        for(Subtask subtask:subtasks){
            SubtaskLayoutWidget subtaskLayoutWidget = new SubtaskLayoutWidget(subtask.getSubtaskName(),subtask.isCompleted(),this);
            subtaskList.addView(subtaskLayoutWidget.getLayout());
            subtaskLayoutWidgets.add(subtaskLayoutWidget);
        }

    }

    private void saveDeadline() {
        //TODO Auslesung aller layout komponenten und speichern in einer deadline
        if (textTaskName.getText().toString().equals("") | textDeadline.getText().toString().equals("")) {
            Toast.makeText(this,"Die Felder Titel und Datum dürfen nicht leer sein",Toast.LENGTH_LONG).show();
        } else {
            ArrayList<Subtask> subtasks = new ArrayList<>();
            for (SubtaskLayoutWidget subtaskLayoutWidget:subtaskLayoutWidgets) {
                subtasks.add(new Subtask(subtaskLayoutWidget.getSubtaskName(),subtaskLayoutWidget.isCompleted()));
            }
            deadline.setDeadlineName(textTaskName.getText().toString());
            deadline.setDeadlineDate(calendar);
            deadline.setNotes(textNotes.getText().toString());
            deadline.setReacurring(checkBoxRecurring.isChecked());
            deadline.setSubtaskList(subtasks);

            Intent returnIntent = new Intent();
            returnIntent.putExtra("deadline",deadline);
            returnIntent.putExtra("isNewDeadline",isNewDeadline);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }

    private void deleteDeadline() {
        if (isNewDeadline) {
            finish();
        } else {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("deleted",true);
            returnIntent.putExtra("deadline",deadline);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
    }

    public void updateDate(int day, int month, int year){
        textDeadline.setText(day + ". " + month + ". " + year + ".");
        calendar.set(year,month,day);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Ask user to confirm leaving the dialog and loosing all changes
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            // Is executed when the user leaves the ManageDeadlinePage
            // without saving or deleting the deadline
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exportDeadline()
    {
       // TODO Export deadline
    }
}
