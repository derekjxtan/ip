import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UI {
    private String name;
    private static final String DATETIME_INPUT_FORMAT = "yyyy-MM-dd HHmm";
    public static final DateTimeFormatter dateTimeInputFormatter = DateTimeFormatter.ofPattern(DATETIME_INPUT_FORMAT);

    public UI(String name) {
        this.name = name;
    }

    public void handleCommand(ArrayList<String> parsedInput, ArrayList<Task> taskList) {}

    public void printWelcomeMessage() {
        String name = "Derek";
        System.out.println("Hello! I'm " + this.name);
        System.out.println("What can I do for you?");
    }

    public void printGoodbyeMessage() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    public void printTaskAddedSuccessfullyMessage(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        this.printTaskCount(taskCount);
    }


    public void printTaskDeletedMessage(Task task, int taskCount) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        this.printTaskCount(taskCount);
    }

    public void printTaskMarkedMessage(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println(task);
    }

    public void printTaskUnmarkedMessage(Task task) {
        System.out.println("OK, I've marked this task as not done yet:");
        System.out.println(task);
    }

    private void printTaskCount(int taskCount) {
        System.out.println("Now you have " + taskCount + (taskCount == 1 ? " task" : " tasks") + " in the list.");
    }
}
