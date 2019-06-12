package com.team.deminder.deminder.customLayoutComponents;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.DeadlineOverviewPage;
import com.team.deminder.deminder.ManageDeadlinePage;
import com.team.deminder.deminder.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DeadlineLayoutWidget {
    private Context context;
    private LinearLayout linearLayout;
    private Deadline deadline;
    private ImageButton imageButton;

    public DeadlineLayoutWidget(Deadline deadline, Context context, int deadlineID, Activity deadlineOverviewPage) {
        this.deadline = deadline;
        this.context = context;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        linearLayout=  new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f);

        LinearLayout.LayoutParams rootLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT,1f);

        linearLayout.setLayoutParams(rootLayoutParams);

        TextView textDeadlineName = new TextView(context);
        textDeadlineName.setText(deadline.getDeadlineName());
        textDeadlineName.setTextSize(20);
        linearLayout.addView(textDeadlineName, textParams);

        TextView textDeadlineDate = new TextView(context);
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long milisecondsApart = deadline.getDeadlineDate().getTimeInMillis() - currentTime;
        double exactDaysApart = (double) milisecondsApart / (1000 * 60 * 60 * 24);
        int daysApart = (int) Math.ceil(exactDaysApart);
        String shownDate;
        if(daysApart<0){
            shownDate = "Abgelaufen";
            textDeadlineDate.setTextColor( Color.rgb(184, 0, 0));
        }
        else if (daysApart == 0) {
            shownDate = "Heute";
            textDeadlineDate.setTextColor( Color.rgb(184, 0, 0));
        } else if (daysApart == 1) {
            shownDate = "Morgen";
            textDeadlineDate.setTextColor( Color.rgb(244,131,66));
        } else if (daysApart <= 7) {
            shownDate = "In " + Integer.toString(daysApart) + " Tagen";
            textDeadlineDate.setTextColor( Color.rgb(244,199,65));
        } else {
            shownDate = dateFormat.format(deadline.getDeadlineDate().getTimeInMillis());
        }


        textDeadlineDate.setText(shownDate);
        textDeadlineDate.setTextSize(20);
        textDeadlineDate.setGravity(Gravity.RIGHT);
        linearLayout.addView(textDeadlineDate, textParams);


/*
        imageButton = new ImageButton(context);
        imageButton.setBackgroundResource(R.drawable.ic_drop_down_grey);
        linearLayout.addView(imageButton);
*/

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageDeadlinePage.class);
                intent.putExtra("deadline", deadline);
                deadlineOverviewPage.startActivityForResult(intent, deadlineID);
            }
        });
    }

    public LinearLayout getLayout(){
        return linearLayout;
    }

    public Deadline getDeadline() {
        return deadline;
    }
}
