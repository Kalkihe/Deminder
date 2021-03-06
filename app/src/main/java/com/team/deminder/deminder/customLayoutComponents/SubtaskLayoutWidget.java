package com.team.deminder.deminder.customLayoutComponents;

import android.content.Context;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.team.deminder.deminder.R;

public class SubtaskLayoutWidget {
    private String subtaskName;
    private boolean completed;
    private Context context;
    private ImageButton buttonDelete;
    private EditText textSubtaskName;
    private CheckBox checkBoxCompleted;

    public LinearLayout getLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        textSubtaskName = new EditText(context);
        textSubtaskName.setText(subtaskName);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.width = 600;
        textSubtaskName.setLayoutParams(params);
        linearLayout.addView(textSubtaskName);

        checkBoxCompleted = new CheckBox(context);
        checkBoxCompleted.setChecked(completed);
        linearLayout.addView(checkBoxCompleted);

        buttonDelete = new ImageButton(context);
        linearLayout.addView(buttonDelete);
        linearLayout.setId(R.id.subtaskLayoutWidget);
        buttonDelete.setImageResource(android.R.drawable.ic_delete);
        buttonDelete.setBackgroundColor(0);

        return linearLayout;
    }

    public ImageButton getDeleteButton() {
        return buttonDelete;
    }

    public SubtaskLayoutWidget(String subtaskName, boolean completed, Context context) {
        this.subtaskName = subtaskName;
        this.completed = completed;
        this.context = context;

    }

    public String getSubtaskName() {
        return textSubtaskName.getText().toString();
    }

    public void setSubtaskName(String subtaskName) {
        this.subtaskName = subtaskName;
    }

    public boolean isCompleted() {
        return checkBoxCompleted.isChecked();
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
