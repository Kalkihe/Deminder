package com.team.deminder.deminder;

import com.team.deminder.deminder.Containers.Deadline;
import com.team.deminder.deminder.Containers.Subtask;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void deadline_isCorrect() {
        Calendar calendar = Calendar.getInstance();
        ArrayList subtasks = new ArrayList();
        subtasks.add(new Subtask("Subtask 1", true));
        subtasks.add(new Subtask("Subtask 2", false));
        Deadline deadline = new Deadline("Test", calendar, "wir testen", subtasks);
        assertEquals("Test", deadline.getDeadlineName());
        assertEquals(subtasks,deadline.getSubtaskList());
        assertEquals("wir testen",deadline.getNotes());
        assertEquals(calendar,deadline.getDeadlineDate());
    }
}