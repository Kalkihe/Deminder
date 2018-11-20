package com.team.deminder.deminder;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.DeadlineLayoutWidget;
import com.team.deminder.deminder.Containers.Deadline;
import java.util.ArrayList;

public class DeadlineOverviewPage {
    private ArrayList deadlineList;
    private SettingsPage settingsPage;
    private StorageManager storageManager;
    private ManageDeadlinePage manageDeadlinePage;
    private DeadlineLayoutWidget deadlineLayoutWidget;

    public DeadlineOverviewPage() {
    }

    private void buildLayout(){

        // nach drücken des buttons zur Erstellung einer neuen Deadline
        // oder beim drücken auf eine deadline
    }
}
