package com.team.deminder.deminder.StorageManager;

import com.team.deminder.deminder.Containers.Deadline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import android.content.Context;

public class DeadlineWriter extends Thread {
    private ArrayList<Deadline> deadlines;
    Context context;
    public DeadlineWriter(ArrayList<Deadline> deadlines, Context context)
    {
        this.deadlines = deadlines;
        this.context = context;
    }

    public void run()
    {
        int index = 0;
        String fileNamePrefix = "deadline_";
        for(Deadline deadline: this.deadlines)
        {
            writeDeadlineToDisk(deadline, fileNamePrefix + Integer.toString(index));
            index++;
        }
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
}
