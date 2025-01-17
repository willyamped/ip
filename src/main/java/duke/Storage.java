package duke;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents the file used to load and save data.
 */
public class Storage {

    private static String[] arr;
    private String filepath;

    /**
     * A constructor to create a Storage object.
     *
     * @param filepath The location of the file to load from and save to.
     */
    Storage(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Reads the list of tasks from a file.
     *
     * @param ls A list to keep track of all tasks.
     */
    public void readFile(TaskList ls) {
        try {
            File myObj = new File(filepath);
            myObj.createNewFile();
            Scanner myScanner = new Scanner(myObj);
            while (myScanner.hasNextLine()) {
                String data = myScanner.nextLine();
                arr = data.split(" ");
                String task = arr[0];
                String description = getDescription(arr);
                int counter = getCounter(arr, 0);
                if (task.charAt(2) == 'D') {
                    createDeadlineTask(counter, arr, ls, description);
                } else if (task.charAt(2) == 'E') {
                    createEventTask(counter, arr, ls, description);
                } else {
                    createTodoTask(arr, ls, description);
                }
            }
            myScanner.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retrieves the description of a task from a file.
     *
     * @param arr The input from the user.
     * @return A description about a task.
     */
    public String getDescription(String[] arr) {
        String description = "";
        for (int i = 1; i < arr.length; i++) {
            if (!(arr[i].charAt(0) == '(')) {
                description += arr[i] + " ";
            } else {
                break;
            }
        }
        return description;
    }

    /**
     * Returns the starting index of the deadline of a task.
     *
     * @param arr The input from the user.
     * @param counter The initial counter value which is set to 0.
     * @return The starting index of a deadline.
     */
    public int getCounter(String[] arr, int counter) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i].charAt(0) == '(') {
                counter = i;
                break;
            }
        }
        return counter;
    }

    /**
     * Writes to a file.
     *
     * @param ls A list to keep track of all files.
     */
    public static void saveFile(TaskList ls) {
        try {
            FileWriter fileWriter = new FileWriter("./duke.txt");
            fileWriter.write(ls.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the deadline of a task saved in a file.
     *
     * @param counter The index where the deadline starts.
     * @param arr A list of words in a file.
     * @return The deadline of a task.
     */
    public String getDeadline(int counter, String[] arr) {
        String deadline = "";
        for (int i = counter; i < arr.length; i++) {
            if (i == counter) {
                deadline += arr[i].substring(1);
            } else if (i == arr.length - 1) {
                deadline += " " + arr[i].substring(0, arr[i].length() - 1);
            } else {
                deadline += " " + arr[i];
            }
        }
        return deadline;
    }

    /**
     * Reads from a file and creates a deadline task.
     *
     * @param counter The starting index of deadline.
     * @param arr The input from the user.
     * @param ls The list of tasks.
     * @param description The description of the task.
     */
    public void createDeadlineTask(int counter, String[] arr, TaskList ls, String description) {
        String deadline = getDeadline(counter, arr);
        Deadline item = new Deadline(description, deadline);
        ls.addTask(item);
        if (arr[0].substring(4).equals("Done")) {
            ls.markAsDone(ls.getSize() - 1);
        }
    }

    /**
     * Reads from a file and creates an event task.
     *
     * @param counter The starting index of deadline.
     * @param arr The input from the user.
     * @param ls The list of tasks.
     * @param description The description of the task.
     */
    public void createEventTask(int counter, String[] arr, TaskList ls, String description) {
        String deadline = getDeadline(counter, arr);
        Event item = new Event(description, deadline);
        ls.addTask(item);
        if (arr[0].substring(4).equals("Done")) {
            ls.markAsDone(ls.getSize() - 1);
        }
    }

    /**
     * Reads from a file and creates a todo event.
     *
     * @param arr The input from the user.
     * @param ls The list of tasks.
     * @param description The description of the task.
     */
    public void createTodoTask(String[] arr, TaskList ls, String description) {
        Todo item = new Todo(description);
        ls.addTask(item);
        if (arr[0].substring(4).equals("Done")) {
            ls.markAsDone(ls.getSize() - 1);
        }
    }


}
