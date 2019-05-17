package com.team.deminder.deminder.StorageManager;

import android.content.Context;

import com.team.deminder.deminder.Containers.Deadline;

public class ICSWritingStrategy implements IDeadlineWritingStrategy {

    private Context context;

    public ICSWritingStrategy(Context context) {
        this.context = context;
    }

    @Override
    public void writeDeadlineToDisk(Deadline deadline, String fileName) {
        //TODO ICS Writing Strategy
    }
}
