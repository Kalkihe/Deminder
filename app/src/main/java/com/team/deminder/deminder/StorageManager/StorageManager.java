package com.team.deminder.deminder.StorageManager;

import com.team.deminder.deminder.Containers.Deadline;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;

public class StorageManager {
    private HashMap settingsList;
    private ArrayList<Deadline> deadlineList;
    private Context context;

    private DeadlineWriterThread deadlineWriterThread;

    private IDeadlineWritingStrategy deadlineWritingStrategy;

    public StorageManager(Context context) {
        this.settingsList = new HashMap();
        this.context = context;

        this.deadlineList = new ArrayList<Deadline>();

        this.deadlineList = this.readDeadlineListFromDisk();

        // Change Strategy for Writing HERE
        this.deadlineWritingStrategy = new ObjectWritingStrategy(this.context);
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
                break;
            }
        }
        // In seperater Methode (am Besten gleiche wie bei saveDeadline()):
        // Konvertiert die deadline liste in das Kalender format und speichert diese auf dem Handy ab.
        // Threads benutzen beim Speichern!
        this.writeDeadlineListToDisk();
    }

    private void writeDeadlineListToDisk()
    {
        // Prüfe, ob bereits ein DeadlineWriterThread erzeugt wurde
        if (this.deadlineWriterThread != null)
        {
            // Falls bereits ein deadlineWriterThread exisitiert und gerade arbeitet
            if (this.deadlineWriterThread.isAlive())
            {
                // Alten Speicherthread abbrechen
                this.deadlineWriterThread.interrupt();
                this.deadlineWriterThread = null;
            }
        }
        // Neuen DeadlineWriterThread (Thread) erzeugen und zu speichernde Liste übergeben
        this.deadlineWriterThread = new DeadlineWriterThread(this.deadlineList, this.context, this.deadlineWritingStrategy);
        deadlineWriterThread.start();
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
