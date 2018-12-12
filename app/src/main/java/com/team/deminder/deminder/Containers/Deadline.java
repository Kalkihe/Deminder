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
    private boolean reacurring;
    private String notes;
    private ArrayList<Subtask> subtaskList;
    private UUID uuid;

    public Deadline(String deadlineName, Calendar deadlineDate, boolean reacurring, String notes, ArrayList subtaskList) {
        this.deadlineName = deadlineName;
        this.deadlineDate = deadlineDate;
        this.reacurring = reacurring;
        this.notes = notes;
        this.subtaskList = subtaskList;
        this.uuid = UUID.randomUUID();
    }

    public Deadline() {

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

    public boolean isReacurring() {
        return reacurring;
    }

    public void setReacurring(boolean reacurring) {
        this.reacurring = reacurring;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Deadline deadline = (Deadline) o;
        return this.uuid.equals(deadline.uuid);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uuid);
    }
}
