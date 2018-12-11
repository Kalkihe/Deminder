package com.team.deminder.deminder.StorageManager;

import com.team.deminder.deminder.Containers.Deadline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;

public class DeadlineWriter extends Thread {
    private ArrayList<Deadline> deadlines;
    private Context context;
    private final String fileNamePrefix = "deadline_";
    private final String fileFormat = ".txt";

    public DeadlineWriter(ArrayList<Deadline> deadlines, Context context)
    {
        this.deadlines = deadlines;
        this.context = context;
    }

    public void run()
    {
        int index = 0;
        for(Deadline deadline: deadlines)
        {
            writeDeadlineToDisk(deadline, this.getFileName(index));
            index++;
        }
        //Lösche alle nicht benötigten Dateien
        this.deleteUnnecessaryDeadlinesFromDisk(index + 1);
    }

    private void writeDeadlineToDisk(Deadline deadline, String fileName)
    {
        try {
            FileOutputStream fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(deadline);
            objectOutputStream.close();
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUnnecessaryDeadlinesFromDisk(int startIndex)
    {
        // Liste aus Dateinamen im App-Context holen
        String[] fileNames = this.context.fileList();
        for (int index = startIndex; index < fileNames.length; index++)
        {
           try{
                File file = new File(this.context.getFilesDir(), this.getFileName(index));
                if (file.exists())
                {
                    file.delete();
                }
           }
           catch (Exception ex) {
               ex.printStackTrace();
           }
        }
    }

    private String getFileName(int index)
    {
        return this.fileNamePrefix + Integer.toString(index) + this.fileFormat;
    }
}
