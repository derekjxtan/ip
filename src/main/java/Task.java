public class Task {
    /**
     * name of task
     */
    private String taskName;
    /**
     * boolean to track whether the task has been marked as done
     */
    private boolean done;

    /**
     * Constructor for the task class
     * tasks are initialised as false
     */
    public Task(String name) {
        this.taskName = name;
        this.done = false;
    }

    public void markAsDone() {
        this.done = true;
    }

    public void markAsUndone() {
        this.done = false;
    }

    @Override
    public String toString() {
        if (this.done) {
            return "[X] " + taskName;
        } else {
            return "[ ] " + taskName;
        }
    }
}
