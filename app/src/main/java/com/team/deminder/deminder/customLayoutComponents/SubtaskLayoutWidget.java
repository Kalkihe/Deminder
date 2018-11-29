package com.team.deminder.deminder.customLayoutComponents;

import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team.deminder.deminder.Containers.Subtask;
import com.team.deminder.deminder.ManageDeadlinePage;

public class SubtaskLayoutWidget {
    private String subtaskName;
    private boolean completed;
    private Context context;

    public LinearLayout getLayout(){
        LinearLayout linearLayout= new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textSubtaskName = new TextView(context);
        textSubtaskName.setText(subtaskName);
        linearLayout.addView(textSubtaskName);
        CheckBox checkBoxCompleted = new CheckBox(context);
        checkBoxCompleted.setChecked(completed);
        linearLayout.addView(checkBoxCompleted);
        ImageButton buttonDelete = new ImageButton(context);
        linearLayout.addView(buttonDelete);
    return linearLayout;
    }

    public SubtaskLayoutWidget(String subtaskName, boolean completed, Context context) {
        this.subtaskName=subtaskName;
        this.completed=completed;
        this.context = context;

    }

    public TextView test(){
        LinearLayout linearLayout=new LinearLayout(context);
        TextView textView = new TextView(context);
        textView.setText("Penis");
        return textView;
    }


    public String getSubtaskName() {
        return subtaskName;
    }

    public void setSubtaskName(String subtaskName) {
        this.subtaskName = subtaskName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
