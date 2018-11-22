package com.team.deminder.deminder.Containers;

public class Subtask {
    private String subtaskName;
    private boolean completed;

    public Subtask() {
    }

    public Subtask(String subtaskName, boolean completed) {
        this.subtaskName = subtaskName;
        this.completed = completed;
    }

    public String getSubtaskName() {
        return subtaskName;
    }

    public void setSubtaskName(String subtaskName) {
        this.subtaskName = subtaskName;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
}
