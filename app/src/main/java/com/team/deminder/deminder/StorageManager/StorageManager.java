package com.team.deminder.deminder.StorageManager;

import com.team.deminder.deminder.Containers.Deadline;

import java.util.ArrayList;
import java.util.HashMap;

public class StorageManager {
    private HashMap settingsList;
    private ArrayList deadlineList;

    public StorageManager() {
    }

    public HashMap loadSettings() {
        return settingsList;
    }

    public ArrayList loadDeadlines() {
        return deadlineList;
    }

    public void saveDeadline(Deadline deadline) {

    }

    public void updateSettings(HashMap settingslist) {
        this.settingsList = settingslist;

    }

    public void deleteDeadline(Deadline deadline) {

    }


}
