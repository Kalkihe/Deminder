package com.team.deminder.deminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.Containers.Subtask;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.SubtaskLayoutWidget;

import java.util.ArrayList;
import java.util.Calendar;
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
    private Button buttonAddSubtask;
    private ImageButton buttonSave;
    private ImageButton buttonDelete;
    private LinearLayout subtaskList;
    private Calendar calendar;

    // Is called when a new ManageDeadlinePage is called
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage_deadline_page);
        storageManager = new StorageManager();
        isNewDeadline = true;
        intent = getIntent();
        deadline = (Deadline) intent.getSerializableExtra("deadline");
        subtaskLayoutWidget = new SubtaskLayoutWidget("ASD",false,this);
        initialiseLayoutComponents();

        if (deadline != null) {
            // Wenn eine deadline mitgeschickt wurde fülle alle componenten mit dessen Daten
            fillLayoutComponents();
            isNewDeadline = false;
        } else{
            deadline = new Deadline("test",new Date(),false,"asdasd",new ArrayList());
        }

    }


    @SuppressLint("CutPasteId")
    private void initialiseLayoutComponents() {

        //TODO use findElementByViewID to bind all layout components to fields
        subtaskList = this.findViewById(R.id.subtaskList);
        textTaskName = this.findViewById(R.id.textTaskName);
        textDeadline = this.findViewById(R.id.textDeadline);
      //  DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        //       @Override
        //  public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {

        //      arg1 = calendar.get(Calendar.YEAR);
        //      arg2 = calendar.get(Calendar.MONTH);
        //      arg3 = calendar.get(Calendar.DAY_OF_MONTH);
        //      int year = arg1;
        //      int month = arg2;
        //      int day = arg3;
        //      textDeadline.setText(new StringBuilder().append(day).append("/")
        //              .append(month).append("/").append(year));
        //  }
        // };
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
        //for(Subtask subtask:subtasks){
         //new SubtaskLayoutWidget(subtask.getSubtaskName(),subtask.isCompleted());
        //}
        SubtaskLayoutWidget subtaskLayoutWidget = new SubtaskLayoutWidget("TastName",  true, this);
        subtaskList.addView(subtaskLayoutWidget.getLayout());

    }

    private void saveDeadline() {
        //TODO Auslesung aller layout komponenten und speichern in einer deadline
        storageManager.saveDeadline(deadline);

        //deadline.setDeadlineName();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("deadline",deadline);
        returnIntent.putExtra("isNewDeadline",isNewDeadline);
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
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
