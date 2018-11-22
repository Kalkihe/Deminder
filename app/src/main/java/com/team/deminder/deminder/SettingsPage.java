package com.team.deminder.deminder;

import com.team.deminder.deminder.StorageManager.StorageManager;

import java.util.HashMap;

public class SettingsPage {
    private final StorageManager storageManager;
    private final HashMap settingsList;

    public SettingsPage() {
        storageManager = new StorageManager();
        settingsList = storageManager.loadSettings();

        buildLayout();
    }

    private void buildLayout(){

        // Muss gecalled werden wenn man speichern muss, zb nach Drücken eines speicher buttons
        // oder einfach wenn man die Seite verlässt.
        storageManager.updateSettings(settingsList);

    }


}
