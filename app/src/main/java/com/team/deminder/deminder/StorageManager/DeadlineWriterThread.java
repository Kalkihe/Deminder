package com.team.deminder.deminder.StorageManager;

import com.team.deminder.deminder.Containers.Deadline;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;

public class DeadlineWriterThread extends Thread {
    private ArrayList<Deadline> deadlines;
    private Context context;
    private IDeadlineWritingStrategy writingStrategy;
    private final String fileNamePrefix = "deadline_";
    private final String fileFormat = ".txt";

    public DeadlineWriterThread(ArrayList<Deadline> deadlines, Context context, IDeadlineWritingStrategy writingStrategy)
    {
        this.deadlines = deadlines;
        this.context = context;
        this.writingStrategy = writingStrategy;
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
        this.deleteUnnecessaryDeadlinesFromDisk(index);
    }

    private void writeDeadlineToDisk(Deadline deadline, String fileName)
    {
        this.writingStrategy.writeDeadlineToDisk(deadline, fileName);
    }

    private void deleteUnnecessaryDeadlinesFromDisk(int startIndex)
    {
        // Liste aus Dateinamen im App-Context holen
        String[] fileNames = this.context.fileList();
        for (int index = startIndex; index < fileNames.length; index++)
        {
           try{
               this.context.deleteFile(this.getFileName(index));

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
