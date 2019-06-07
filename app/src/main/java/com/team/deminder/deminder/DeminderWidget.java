package com.team.deminder.deminder;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.RemoteViews;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.StorageManager.StorageManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DeminderWidget extends AppWidgetProvider {
    private ArrayList<Deadline> deadlineList = new ArrayList<>();
    private StorageManager storageManager;

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        final AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        final ComponentName cn = new ComponentName(context, DeminderWidget.class);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(cn);

        if (intent.getAction() == "Deminder.UPDATE_DEADLINE") {
            final Deadline deadline1 = (Deadline) intent.getSerializableExtra("deadline1");
            final Deadline deadline2 = (Deadline) intent.getSerializableExtra("deadline2");
            final Deadline deadline3 = (Deadline) intent.getSerializableExtra("deadline3");
            final Deadline deadline4 = (Deadline) intent.getSerializableExtra("deadline4");

            if (deadline1 != null) {
                deadlineList.add(deadline1);
            }
            if (deadline2 != null) {
                deadlineList.add(deadline2);
            }
            if (deadline3 != null) {
                deadlineList.add(deadline3);
            }
            if (deadline4 != null) {
                deadlineList.add(deadline4);
            }
            updateTexts(context, appWidgetManager, appWidgetIds);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        storageManager = new StorageManager(context);
        deadlineList = storageManager.loadDeadlines();
        updateTexts(context,appWidgetManager,appWidgetIds);
    }

    private void updateTexts(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int N = appWidgetIds.length;

        // Perform this loop procedure for each App Widget that belongs to this provider
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];

            Intent intent = new Intent(context, DeadlineOverviewPage.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.deminder_widget_page);
            views.setOnClickPendingIntent(R.id.rootLayout, pendingIntent);
            views.setViewVisibility(R.id.deadline1Root, View.INVISIBLE);
            views.setViewVisibility(R.id.deadline2Root,View.INVISIBLE);
            views.setViewVisibility(R.id.deadline3Root,View.INVISIBLE);
            views.setViewVisibility(R.id.deadline4Root,View.INVISIBLE);

            if (deadlineList.size() > 0) {
                views.setViewVisibility(R.id.deadline1Root, View.VISIBLE);
                views.setTextViewText(R.id.deadline1, deadlineList.get(0).getDeadlineName());
                setDateText(deadlineList.get(0),views,R.id.datum1);
            }
            if (deadlineList.size() > 1) {
                views.setTextViewText(R.id.deadline2, deadlineList.get(1).getDeadlineName());
                setDateText(deadlineList.get(1),views,R.id.datum2);
                views.setViewVisibility(R.id.deadline2Root,View.VISIBLE);
            }
            if (deadlineList.size() > 2) {
                views.setTextViewText(R.id.deadline3, deadlineList.get(2).getDeadlineName());
                setDateText(deadlineList.get(2),views,R.id.datum3);
                views.setViewVisibility(R.id.deadline3Root,View.VISIBLE);
            }
            if (deadlineList.size() > 3) {
                views.setTextViewText(R.id.deadline4, deadlineList.get(3).getDeadlineName());
                setDateText(deadlineList.get(3),views,R.id.datum4);
                views.setViewVisibility(R.id.deadline4Root,View.VISIBLE);
            }

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }
    }

    private void setDateText(Deadline deadline, RemoteViews views , int viewID) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        long currentTime = Calendar.getInstance().getTimeInMillis();
        long milisecondsApart = deadline.getDeadlineDate().getTimeInMillis() - currentTime;
        double exactDaysApart = (double) milisecondsApart / (1000 * 60 * 60 * 24);
        int daysApart = (int) Math.ceil(exactDaysApart);
        String shownDate;

        if (daysApart == 0) {
            shownDate = "Heute";
            views.setTextColor(viewID, Color.RED);
        } else if (daysApart <= 7) {
            shownDate = "In " + Integer.toString(daysApart) + " Tagen";
            views.setTextColor(viewID, Color.YELLOW);
        } else {
            shownDate = dateFormat.format(deadlineList.get(0).getDeadlineDate().getTime());
        }
        views.setTextViewText(viewID,shownDate);
        return;
    }
}
