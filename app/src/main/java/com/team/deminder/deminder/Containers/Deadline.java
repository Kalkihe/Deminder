package com.team.deminder.deminder.Containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Deadline implements Serializable {
    private String deadlineName;
    private Calendar deadlineDate;
    private String notes;
    private ArrayList<Subtask> subtaskList;
    private UUID uuid;
    private String fileNameOnStorage;

    public Deadline(String deadlineName, Calendar deadlineDate, String notes, ArrayList subtaskList) {
        this.deadlineName = deadlineName;
        this.deadlineDate = deadlineDate;
        this.notes = notes;
        this.subtaskList = subtaskList;
        this.uuid = UUID.randomUUID();
        this.fileNameOnStorage = "";
    }

    public Deadline() {
        this.uuid = UUID.randomUUID();
    }

    public String getDeadlineName() {
        return deadlineName;
    }

    public void setDeadlineName(String deadlineName) {
        this.deadlineName = deadlineName;
    }

    public Calendar getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Calendar deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList getSubtaskList() {
        return subtaskList;
    }

    public void setSubtaskList(ArrayList subtaskList) {
        this.subtaskList = subtaskList;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getFileNameOnStorage() {
        return fileNameOnStorage;
    }

    public void setFileNameOnStorage(String fileNameOnStorage) {
        this.fileNameOnStorage = fileNameOnStorage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deadline deadline = (Deadline) o;
        return this.uuid.equals(deadline.getUuid());
    }

    @Override
    public int hashCode() {
        return this.uuid.hashCode();
    }
}
