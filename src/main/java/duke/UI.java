package duke;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UI {
    private String name;
    private static final String DATETIME_INPUT_FORMAT = "yyyy-MM-dd HHmm";
    public static final DateTimeFormatter dateTimeInputFormatter = DateTimeFormatter.ofPattern(DATETIME_INPUT_FORMAT);

    /**
     * Constructor for the UI class.
     *
     * @param name Name of the chatbot.
     */
    public UI(String name) {
        this.name = name;
    }

    /**
     * Prints a welcome message.
     */
    public void printWelcomeMessage() {
        System.out.println("Hello! I'm " + this.name);
        System.out.println("What can I do for you?");
    }

    /**
     * Prints a goodbye message.
     */
    public void printGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints a task added message.
     */
    public void printTaskAddedMessage(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        this.printTaskCount(taskCount);
    }

    /**
     * Prints a task deleted message.
     */
    public void printTaskDeletedMessage(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        this.printTaskCount(taskCount);
    }

    /**
     * Prints a task marked message.
     */
    public void printTaskMarkedMessage(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    /**
     * Prints a task unmarked message.
     */
    public void printTaskUnmarkedMessage(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    /**
     * Prints the tasks on a date.
     */
    public void printTasksOn(ArrayList<Task> tasks) {
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void printTasksMatching(ArrayList<Task> tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Prints a loading error message.
     */
    public void printLoadingErrorMessage() {
        System.out.println("Stored data could not be loaded");
    }

    /**
     * Prints a task count message.
     */
    private void printTaskCount(int taskCount) {
        System.out.println("Now you have " + taskCount + (taskCount == 1 ? " task" : " tasks") + " in the list.");
    }
}
