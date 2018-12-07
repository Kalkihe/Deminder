package com.team.deminder.deminder.StorageManager;

import com.team.deminder.deminder.Containers.Deadline;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


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
        // TODO irrelevant bis Settings implementiert werden
        return settingsList;
    }

    public void updateSettings(HashMap settingslist) {
        //TODO irrellevant bis Settings immplementiert werden
        this.settingsList = settingslist;
    }

    // Gibt Liste mit allen Deadline-Elementen zurück
    public ArrayList loadDeadlines() {
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
        this.writeDeadlineListToDisk();
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
        this.writeDeadlineListToDisk();
    }

    private void writeDeadlineListToDisk(ArrayList<Deadline> deadlines)
    {
        int index = 0;
        String fileNamePrefix = "deadline_";
        for(Deadline deadline: deadlines)
        {
            writeDeadlineToDisk(deadline, fileNamePrefix + Integer.toString(index));
            index++;
        }
    }

    private ArrayList<Deadline> readDeadlineListFromDisk()
    {
        // Lokale Arraylist für Deadlines neu erstellen
        ArrayList<Deadline> deadlines = new ArrayList<Deadline>();
        // Liste aus Dateinamen im App-Context holen
        String[] fileNames = context.fileList();
        // Über alle gefundenen Dateien iterieren
        for (int index = 0; index < fileNames.length; index++)
        {
            // TODO: Prüfen, ob aktuelle Datei auch eine Kalenderdatei (und nicht Settings etc. ist)
            Deadline deadline = readDeadlineFromDisk(fileNames[i]);
            // Prüfe, ob bei Einlesen der Deadline ein Fehler vorlag
            if (deadline != null)
            {
                // Füge Deadline zur Liste hinzu, falls kein Fehler auftrat
                deadlines.add(deadline);
            }
        }
        return deadlines;
    }

    private void writeDeadlineToDisk(Deadline deadline, String fileName)
    {
        FileOutputStream fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutPutStream);
            objectOutputStream.writeObject(deadline);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Deadline readDeadlineFromDisk(String fileName)
    {
        try
        {
            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Deadline deadline = (Deadline) objectInputStream.readObject();
            objectInputStream.close();
            fileInputStream.close();
            return deadline;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

}
