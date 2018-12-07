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

    public StorageManager(Context context) {
        this.settingsList = new HashMap();
        this.deadlineList = new ArrayList<Deadline>();
        this.context = context;

        // Wird ausgeführt, wenn man den Button "StorageManager Test" drückt
        // Hieraus bitte alle Tests ausführen

        // z.B. so:
        //Deadline testDeadline = new Deadline("TestDeadline",new Date(),false,"Notizen",new ArrayList());
        //saveDeadline(testDeadline);

        //Deadline testDeadline = new Deadline("TestDeadline",new Date(),false,"Notizen",new ArrayList());
        //Deadline testDeadline2 = new Deadline("TestDeadline2",new Date(),true,"Hallo",new ArrayList());
        //Deadline testDeadline3 = new Deadline("TestDeadline3",new Date(),false,"Notizen",new ArrayList());

        //saveDeadline(testDeadline);
        //saveDeadline(testDeadline2);
        //saveDeadline(testDeadline3);

        //deleteDeadline(testDeadline2);

        // writeDeadlineListToDisk();

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

    private void writeDeadlineListToDisk()
    {
        // Neuen DeadlineWriter (Thread) erzeugen und zu speichernde Liste übergeben
        DeadlineWriter deadlineWriter = new DeadlineWriter(this.deadlineList,context);
        // Thread starten
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
