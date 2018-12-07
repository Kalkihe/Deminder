package com.team.deminder.deminder.customLayoutComponents;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.DeadlineOverviewPage;
import com.team.deminder.deminder.ManageDeadlinePage;
import com.team.deminder.deminder.R;

import java.util.ArrayList;
import java.util.Date;

public class DeadlineLayoutWidget {
    private Context context;
    private String deadlineName;
    private String deadlineDate;
    private LinearLayout linearLayout;

    public DeadlineLayoutWidget(String deadlineName, String deadlineDate, Context context) {
        this.deadlineName=deadlineName;
        this.deadlineDate=deadlineDate;
        this.context = context;
    }

    public LinearLayout getLayout(){
        LinearLayout linearLayout= new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        TextView textDeadlineName = new TextView(context);
        textDeadlineName.setText(deadlineName);
        linearLayout.addView(textDeadlineName);
        TextView textDeadlineDate = new TextView(context);
        textDeadlineDate.setText(deadlineDate);
        linearLayout.addView(textDeadlineDate);
        return linearLayout;
    }
}
