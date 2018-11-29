package com.team.deminder.deminder.customLayoutComponents;

public class SubtaskLayoutWidget {
    private String subtaskName;
    private boolean completed;

    public SubtaskLayoutWidget(String subtaskName, boolean completed) {
        this.subtaskName=subtaskName;
        this.completed=completed;
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
