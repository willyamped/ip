package duke;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TaskList {
    private ArrayList<Task> ls;

    TaskList(ArrayList<Task> ls) {
        this.ls = ls;
    }

    /**
     * A method to get the description from a task entered by the user
     *
     * @param arr The array that contains strings from the user input
     * @return A string containing the description only
     */
    public static String getDescription(String[] arr) {
        String str = "";
        for(int i = 1; i < arr.length; i++) {
            if (!(arr[i].charAt(0) == '/')) {
                str += arr[i] + " ";
            }
            else {
                break;
            }
        }
        return str;
    }

    /**
     * A method to get the deadline from a task entered by the user
     *
     * @param arr The array that contains strings from the user input
     * @return A string containing the deadline only
     */
    public static String getDeadline(String[] arr) throws DateTimeParseException {
        String str = "";
        boolean bool = false;
        for(int i = 1; i < arr.length; i++) {
            if (!bool) {
                if (arr[i].charAt(0) == '/') {
                    bool = true;
                    str += arr[i].substring(1) + ": ";
                }
            } else {
                if (arr[i].length() == 10 && arr[i].charAt(4) == '-' && arr[i].charAt(7) == '-') {
                    LocalDate ld = LocalDate.parse(arr[i]);
                    str += ld.getDayOfMonth() + " "
                            + ld.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH) + " "
                            + ld.getYear();
                    if (i != arr.length - 1) {
                        str += " ";
                    }
                } else if (arr[i].length() == 5 && arr[i].charAt(2) == ':') {
                    LocalTime lt = LocalTime.parse(arr[i]);
                    int hour = lt.getHour();
                    String hourSuffix = hour < 12 ? "AM" : "PM";
                    if (hour == 0) {
                        hour = 12;
                    } else if (hour > 12) {
                        hour -= 12;
                    }
                    int minute = lt.getMinute();
                    String minutePrefix = minute < 10 ? "0" : "";
                    str += hour + ":" + minutePrefix + minute + " " + hourSuffix;
                    if (i != arr.length - 1) {
                        str += " ";
                    }

                } else {
                    str += arr[i];
                    if (i != arr.length - 1) {
                        str += " ";
                    }
                }
            }
        }
        return str;
    }

    public void addTask(Task task) {
        ls.add(task);
    }

    public void removeTask(int index) {
        ls.remove(index);
    }

    public void markAsDone(int index) {
        ls.get(index).markAsDone();
    }

    public Task getTask(int index) {
        return ls.get(index);
    }

    public int getSize() {
        return ls.size();
    }

    public void displayList() {
        if (ls.size() == 0) {
            System.out.println("You currently do not have any task!");
        } else {
            System.out.println("Here are the tasks in your list: ");
            for (int i = 0; i < ls.size(); i++) {
                System.out.println((i + 1) + "." + ls.get(i).toString());
            }
        }
    }

    public void printAddTask() {
        System.out.println("Got it. I've added this task: ");
        System.out.println(ls.get(ls.size() - 1).toString());
        System.out.println("Now you have " + ls.size() + " tasks in the list.");
    }
}