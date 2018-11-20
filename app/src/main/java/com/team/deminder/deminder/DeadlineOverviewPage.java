package com.team.deminder.deminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.team.deminder.deminder.Containers.Deadline;

public class DeadlineOverviewPage extends AppCompatActivity {
    private final SettingsPage settingsPage;
    private ManageDeadlinePage manageDeadlinePage;

    public DeadlineOverviewPage() {
        settingsPage = new SettingsPage();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void buildLayout(){

        // nach drücken des buttons zur Erstellung einer neuen Deadline
        // oder beim drücken auf eine deadline

        manageDeadlinePage = new ManageDeadlinePage(new Deadline());
        Intent intent = new Intent(this,ManageDeadlinePage.class);
        intent.

    }


}
