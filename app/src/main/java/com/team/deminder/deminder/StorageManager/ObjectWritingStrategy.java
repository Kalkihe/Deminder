package com.team.deminder.deminder.StorageManager;

import android.content.Context;

import com.team.deminder.deminder.Containers.Deadline;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

import static android.content.Context.MODE_PRIVATE;

public class ObjectWritingStrategy implements IDeadlineWritingStrategy {

    private Context context;

    public ObjectWritingStrategy(Context context) {
        this.context = context;
    }

    @Override
    public void writeDeadlineToDisk(Deadline deadline, String fileName) {
        // Deklariere Streams
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            deadline.setFileNameOnStorage(fileName);
            // Initialisiere Streams
            fileOutputStream = context.openFileOutput(fileName, MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            // Speichere Deadline
            objectOutputStream.writeObject(deadline);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            // Schließe Streams, egal ob schreiben erfolgreich war oder nicht
            try {
                if (fileOutputStream != null)
                {
                    fileOutputStream.close();
                }
                if (objectOutputStream != null)
                {
                    objectOutputStream.close();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }
}
