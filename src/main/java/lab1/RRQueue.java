package lab1;

import java.util.LinkedList;

public class RRQueue  {

    private LinkedList<Task>  tasks;

    public RRQueue() {
        this.tasks = new LinkedList<>();
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task get() {
        return tasks.pop();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
