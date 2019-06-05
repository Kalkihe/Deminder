package com.team.deminder.deminder;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.DeadlineLayoutWidget;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DeminderWidget extends AppWidgetProvider {
    private ArrayList<Deadline> deadlineList = new ArrayList<>();
    private StorageManager storageManager;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;
        storageManager = new StorageManager(context);
        deadlineList = storageManager.loadDeadlines();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");


        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            // Create an Intent to launch ExampleActivity
            Intent intent = new Intent(context, DeadlineOverviewPage.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            // Get the layout for the App Widget and attach an on-click listener
            // to the button
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.deminder_widget_page);
            views.setOnClickPendingIntent(R.id.rootLayout, pendingIntent);

            if (deadlineList.size() > 0) {
                views.setTextViewText(R.id.deadline1, deadlineList.get(0).getDeadlineName());
                views.setTextViewText(R.id.datum1, dateFormat.format(deadlineList.get(0).getDeadlineDate().getTime()));
            }
            if (deadlineList.size() > 1) {
                views.setTextViewText(R.id.deadline2, deadlineList.get(1).getDeadlineName());
                views.setTextViewText(R.id.datum2, dateFormat.format(deadlineList.get(1).getDeadlineDate().getTime()));
            }
            if (deadlineList.size() > 2) {
                views.setTextViewText(R.id.deadline3, deadlineList.get(2).getDeadlineName());
                views.setTextViewText(R.id.datum3, dateFormat.format(deadlineList.get(2).getDeadlineDate().getTime()));
            }
            if (deadlineList.size() > 3) {
                views.setTextViewText(R.id.deadline4, deadlineList.get(3).getDeadlineName());
                views.setTextViewText(R.id.datum4, dateFormat.format(deadlineList.get(3).getDeadlineDate().getTime()));
            }



            //DeadlineLayoutWidget deadlineLayoutWidget = new DeadlineLayoutWidget(deadline, this, this.deadlineID,this);


            // Tell the AppWidgetManager to perform an update on the current app widget
            appWidgetManager.updateAppWidget(appWidgetId, views);
            System.out.println("I am updateing");
        }
    }
}
