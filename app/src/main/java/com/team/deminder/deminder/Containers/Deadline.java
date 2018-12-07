package com.team.deminder.deminder.Containers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Deadline implements Serializable {
    private String deadlineName;
    private Date deadlineDate;
    private boolean reacurring;
    private String notes;
    private ArrayList<Subtask> subtaskList;

    public Deadline(String deadlineName, Date deadlineDate, boolean reacurring, String notes, ArrayList subtaskList) {
        this.deadlineName = deadlineName;
        this.deadlineDate = deadlineDate;
        this.reacurring = reacurring;
        this.notes = notes;
        this.subtaskList = subtaskList;
    }

    public Deadline() {

    }

    public String getDeadlineName() {
        return deadlineName;
    }

    public void setDeadlineName(String deadlineName) {
        this.deadlineName = deadlineName;
    }

    public Date getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(Date deadlineDate) {
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
}
