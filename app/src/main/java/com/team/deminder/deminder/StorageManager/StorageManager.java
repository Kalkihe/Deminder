package com.team.deminder.deminder.StorageManager;

import com.team.deminder.deminder.Containers.Deadline;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;

public class StorageManager {
    private HashMap settingsList;
    private ArrayList<Deadline> deadlineList;
    private Context context;

    private DeadlineWriter deadlineWriter;

    public StorageManager(Context context) {
        this.settingsList = new HashMap();
        this.context = context;

        this.deadlineList = new ArrayList<Deadline>();

        this.deadlineList = this.readDeadlineListFromDisk();
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
        for (int index = 0; index < this.deadlineList.size(); index++)
        {
            Deadline currentDeadline = this.deadlineList.get(index);
            if (currentDeadline.equals(deadline))
            {
                this.deadlineList.remove(index);
                // Index verringern, da nun eine Deadline weniger in der Collection ist
                return;
            }
        }
        // In seperater Methode (am Besten gleiche wie bei saveDeadline()):
        // Konvertiert die deadline liste in das Kalender format und speichert diese auf dem Handy ab.
        // Threads benutzen beim Speichern!
        this.writeDeadlineListToDisk();
    }

    private void writeDeadlineListToDisk()
    {
        // Prüfe, ob bereits ein DeadlineWriter erzeugt wurde
        if (this.deadlineWriter != null)
        {
            // Falls bereits ein deadlineWriter exisitiert und gerade arbeitet
            if (this.deadlineWriter.isAlive())
            {
                // Alten Speicherthread abbrechen
                this.deadlineWriter.interrupt();
                this.deadlineWriter = null;
            }
        }
        // Neuen DeadlineWriter (Thread) erzeugen und zu speichernde Liste übergeben
        this.deadlineWriter = new DeadlineWriter(this.deadlineList, this.context);
        deadlineWriter.start();
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
            if (!fileNames[index].equals("instant-run")) {
                Deadline deadline = readDeadlineFromDisk(fileNames[index]);

                if (deadline != null)
                {
                    // Füge Deadline zur Liste hinzu, falls kein Fehler auftrat
                    deadlines.add(deadline);
                }
            }
            // Prüfe, ob bei Einlesen der Deadline ein Fehler vorlag

        }
        return deadlines;
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
