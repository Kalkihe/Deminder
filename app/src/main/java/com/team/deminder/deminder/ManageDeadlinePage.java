package com.team.deminder.deminder;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.Containers.Subtask;
import com.team.deminder.deminder.StorageManager.StorageManager;
import com.team.deminder.deminder.customLayoutComponents.SubtaskLayoutWidget;

import java.util.ArrayList;

public class ManageDeadlinePage {
    private Deadline deadline;
    private StorageManager storageManager;
    private SubtaskLayoutWidget subtaskLayoutWidget;

    // If new deadline is created
    public ManageDeadlinePage() {
    }

    //If existing deadline is edited
    public ManageDeadlinePage(Deadline deadline, StorageManager storageManager, SubtaskLayoutWidget subtaskLayoutWidget) {
        this.deadline = deadline;
        this.storageManager = storageManager;
        this.subtaskLayoutWidget = subtaskLayoutWidget;
    }

}
