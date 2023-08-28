import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Duke {
    private static ArrayList<Task> tasks;
    private static final String FILE_PATH = "./data/state.txt";
    private static final String DATETIME_INPUT_FORMAT = "yyyy-MM-dd HHmm";
    public static final DateTimeFormatter dateTimeInputFormatter = DateTimeFormatter.ofPattern(DATETIME_INPUT_FORMAT);
    private static final String DATE_INPUT_FORMAT = "yyyy-MM-dd";
    public static final DateTimeFormatter dateInputFormatter = DateTimeFormatter.ofPattern(DATE_INPUT_FORMAT);
    public static void main(String[] args) {
        Duke.tasks = new ArrayList<>();
        Scanner in = new Scanner(System.in);
        Duke.loadState();

        String name = "Derek";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");

        while (true) {
            String line = in.nextLine();
            try {
                ArrayList<String> parsedInput = Parser.parseUserInput(line);
                String command = parsedInput.get(0);

                if (command.equals(Command.MARK.getCommand())) {
                    int index = Integer.parseInt(parsedInput.get(1)) - 1;
                    if (!Duke.isTaskIndexValid(index)) {
                        throw new InvalidCommandException("☹ OOPS!!! The task index in invalid");
                    }
                    Task task = Duke.tasks.get(index);
                    task.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task);
                    continue;
                }
                if (command.equals(Command.UNMARK.getCommand())) {
                    int index = Integer.parseInt(parsedInput.get(1)) - 1;
                    if (!Duke.isTaskIndexValid(index)) {
                        throw new InvalidCommandException("☹ OOPS!!! The task index in invalid");
                    }
                    Task task = Duke.tasks.get(index);
                    task.markAsUndone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(task);
                    continue;
                }
                if (command.equals(Command.LIST.getCommand())) {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < Duke.tasks.size(); i++) {
                        System.out.println((i + 1) + "." + Duke.tasks.get(i));
                    }
                    continue;
                }
                if (command.equals(Command.BYE.getCommand())) {
                    Duke.saveState();
                    System.out.println("Bye. Hope to see you again soon!");
                    break;
                }
                if (command.equals(Command.TODO.getCommand())) {
                    ToDo newTodo = new ToDo(parsedInput.get(1));
                    Duke.tasks.add(newTodo);
                    Duke.printTaskAddedMessages(newTodo);
                    continue;
                }
                if (command.equals(Command.DEADLINE.getCommand())) {
                    Deadline newDeadline = new Deadline(parsedInput.get(1),
                            LocalDateTime.parse(parsedInput.get(2), dateTimeInputFormatter));
                    Duke.tasks.add(newDeadline);
                    Duke.printTaskAddedMessages(newDeadline);
                    continue;
                }
                if (command.equals(Command.EVENT.getCommand())) {
                    Event newEvent = new Event(parsedInput.get(1),
                            LocalDateTime.parse(parsedInput.get(2), dateTimeInputFormatter),
                            LocalDateTime.parse(parsedInput.get(3), dateTimeInputFormatter));
                    Duke.tasks.add(newEvent);
                    Duke.printTaskAddedMessages(newEvent);
                    continue;
                }
                if (command.equals(Command.DELETE.getCommand())) {
                    int index = Integer.parseInt(parsedInput.get(1)) - 1;
                    if (!Duke.isTaskIndexValid(index)) {
                        throw new InvalidCommandException("☹ OOPS!!! The task index in invalid");
                    }
                    Task task = Duke.tasks.remove(index);
                    Duke.printTaskDeletedMessage(task);
                    continue;
                }
                if (command.equals(Command.ON.getCommand())) {
                    LocalDate date = LocalDate.parse(parsedInput.get(1));
                    for (int i = 0; i < Duke.tasks.size(); i++) {
                        if (Duke.tasks.get(i).isOnDate(date)) {
                            System.out.println((i + 1) + "." + Duke.tasks.get(i));
                        }
                    }
                    continue;
                }
                throw new InvalidCommandException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }
        in.close();
    }

    private static void printTaskAddedMessages(Task task) {
        System.out.println("Got it. I've added this task:");
        System.out.println(task);
        Duke.printTaskCount();
    }

    private static void printTaskDeletedMessage(Task task) {
        System.out.println("Noted. I've removed this task:");
        System.out.println(task);
        Duke.printTaskCount();
    }

    private static void printTaskCount() {
        int tasksCount = Duke.tasks.size();
        System.out.println("Now you have " + tasksCount + (tasksCount == 1 ? " task" : " tasks") + " in the list.");
    }

    private static boolean isTaskIndexValid(int index) {
        return index >= 0 && index < Duke.tasks.size();
    }

    private static void loadState() {
        try {
            File f = new File(Duke.FILE_PATH);
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String[] taskArray = s.nextLine().split(" / ");
                Task task;

                if (taskArray[0].equals(Command.TODO.getCommand())) {
                    task = new ToDo(taskArray[2]);
                } else if (taskArray[0].equals(Command.DEADLINE.getCommand())) {
                    task = new Deadline(taskArray[2], LocalDateTime.parse(taskArray[3], dateTimeInputFormatter));
                } else {
                    task = new Event(taskArray[2], LocalDateTime.parse(taskArray[3], dateTimeInputFormatter),
                            LocalDateTime.parse(taskArray[4], dateTimeInputFormatter));
                }

                if (taskArray[1].equals("1")) {
                    task.markAsDone();
                }
                Duke.tasks.add(task);
            }
            System.out.println("Successfully loaded saved state");
        } catch (FileNotFoundException e) {
            System.out.println("File to save state cannot be found");
        }
    }

    private static void saveState() {
        try {
            FileWriter fw = new FileWriter(Duke.FILE_PATH);
            for (int i = 0; i < Duke.tasks.size(); i++) {
                fw.write(Duke.tasks.get(i).toSaveStateString() + "\n");
            }
            fw.close();
            System.out.println("Sucessfully saved state");
        } catch (IOException e) {
            System.out.println("Failed to save state");
        }
    }
}
