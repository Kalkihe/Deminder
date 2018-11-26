package com.team.deminder.deminder.StorageManager;

import com.team.deminder.deminder.Containers.Deadline;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class StorageManager {
    private HashMap settingsList;
    private ArrayList<Deadline> deadlineList;

    public StorageManager() {
        this.settingsList = new HashMap();
        this.deadlineList = new ArrayList<Deadline>();

        // Wird ausgeführt, wenn man den Button "StorageManager Test" drückt
        // Hieraus bitte alle Tests ausführen

        // z.B. so:
        //Deadline testDeadline = new Deadline("TestDeadline",new Date(),false,"Notizen",new ArrayList());
        //saveDeadline(testDeadline);
    }

    public HashMap loadSettings() {
        return settingsList;
    }

    public void updateSettings(HashMap settingslist) {
        //TODO irellevant bis Settings feststehen
        this.settingsList = settingslist;
    }

    public ArrayList loadDeadlines() {
        // Soll eine Liste von allen Deadline objekten zurückgeben
        return deadlineList;
    }

    public void saveDeadline(Deadline deadline) {
        // Nimmt eine Deadline, fügt diese in die deadlineList ein.
        if (! (this.deadlineList.contains(deadline)))
        {
            this.deadlineList.add(deadline);
        }
        else
        {
            // Fehlermeldung?
        }
        // In seperater Methode:
        // Konvertiert die deadline liste in das Kalender format und speichert diese auf dem Handy ab.
        // Threads benutzen beim Speichern!
        this.saveDeadlineListToDisk();
    }

    public void deleteDeadline(Deadline deadline) {
        // Nimmt eine Deadline, löscht diese aus der deadlineList
        if (this.deadlineList.contains(deadline))
        {
            this.deadlineList.remove(deadline);
        }
        else
        {
           // Fehlermeldung?
        }

        // In seperater Methode (am Besten gleiche wie bei saveDeadline()):
        // Konvertiert die deadline liste in das Kalender format und speichert diese auf dem Handy ab.
        // Threads benutzen beim Speichern!
        this.saveDeadlineListToDisk();
    }

    private void saveDeadlineListToDisk()
    {
        
    }




}
