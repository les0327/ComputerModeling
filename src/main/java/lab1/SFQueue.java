package lab1;

import lombok.Getter;

import java.util.Comparator;
import java.util.LinkedList;

public class SFQueue {

    @Getter private LinkedList<Task> tasks;

    public SFQueue() {
        tasks = new LinkedList<>();
    }

    public void add(Task task) {
        tasks.add(task);
        tasks.sort(Comparator.comparingDouble(Task::getSolveTime));
    }

    public Task get() {
        return tasks.pop();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
