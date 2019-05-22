package com.team.deminder.deminder.StorageManager;

import com.team.deminder.deminder.Containers.Deadline;

public interface IDeadlineWritingStrategy {
    void writeDeadlineToDisk(Deadline deadline, String fileName);
}
