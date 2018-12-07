package com.team.deminder.deminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.team.deminder.deminder.customLayoutComponents.SubtaskLayoutWidget;

import java.util.ArrayList;
import java.util.Date;

// This will soon be an fragment instead of an activity
public class ManageDeadlinePage extends AppCompatActivity {
    private Deadline deadline;
    private StorageManager storageManager;
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
    private LinearLayout subtaskList;
    ArrayList<SubtaskLayoutWidget> subtaskLayoutWidgets;

    // Is called when a new ManageDeadlinePage is called
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_deadline_page);
        storageManager = new StorageManager();
        isNewDeadline = true;
        subtaskLayoutWidgets = new ArrayList<>();

        intent = getIntent();
        deadline = (Deadline) intent.getSerializableExtra("deadline");
        subtaskLayoutWidget = new SubtaskLayoutWidget("ASD",false,this);
        initialiseLayoutComponents();

        if (deadline != null) {
            // Wenn eine deadline mitgeschickt wurde fülle alle componenten mit dessen Daten
            fillLayoutComponents();
            isNewDeadline = false;
        } else {
            deadline = new Deadline();
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
        textDeadline.setText(deadline.getDeadlineDate().toString());
        if (deadline.isReacurring()){
            checkBoxRecurring.isChecked();
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
        storageManager.saveDeadline(deadline);

        if (textTaskName.getText().toString().equals("") | textDeadline.getText().toString().equals("")) {
            Toast.makeText(this,"Die Felder Titel und Datum dürfen nicht leer sein",Toast.LENGTH_LONG).show();
        } else {
            ArrayList<Subtask> subtasks = new ArrayList<>();
            for (SubtaskLayoutWidget subtaskLayoutWidget:subtaskLayoutWidgets) {
                subtasks.add(new Subtask(subtaskLayoutWidget.getSubtaskName(),subtaskLayoutWidget.isCompleted()));
            }
            deadline.setDeadlineName(textTaskName.getText().toString());
            deadline.setDeadlineDate(new Date());
            deadline.setNotes(textNotes.getText().toString());
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
            storageManager.deleteDeadline(deadline);
            Intent returnIntent = new Intent();
            returnIntent.putExtra("deleted",true);
            returnIntent.putExtra("deadline",deadline);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }
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
}
