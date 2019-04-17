package com.team.deminder.deminder.customLayoutComponents;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.DeadlineOverviewPage;
import com.team.deminder.deminder.ManageDeadlinePage;
import com.team.deminder.deminder.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DeadlineLayoutWidget {
    private Context context;
    private LinearLayout linearLayout;
    private Deadline deadline;
    private ImageButton imageButton;

    public DeadlineLayoutWidget(Deadline deadline, Context context) {
        this.deadline = deadline;
        this.context = context;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        linearLayout=  new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(5, 0, 60, 40);
        TextView textDeadlineName = new TextView(context);
        textDeadlineName.setText(deadline.getDeadlineName());
        textDeadlineName.setTextSize(20);
        linearLayout.addView(textDeadlineName, layoutParams);

        TextView textDeadlineDate = new TextView(context);
        textDeadlineDate.setText(dateFormat.format(deadline.getDeadlineDate().getTime()));
        textDeadlineDate.setTextSize(20);
        linearLayout.addView(textDeadlineDate, layoutParams);

        imageButton = new ImageButton(context);
        linearLayout.addView(imageButton,layoutParams);
    }

    public LinearLayout getLayout(){
        return linearLayout;
    }

    public Deadline getDeadline() {
        return deadline;
    }
}
