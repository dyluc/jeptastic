package loom.prerequisites;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * Logs items of a list in order. This class represents a RecursiveAction which accepts a list of strings, defining a method to divide the list if a threshold is exceeded
 * and a method to log the contents of a list.
 */
public class ListLoggingRecursiveAction extends RecursiveAction {

    private final String name; // random identifier for logger just to see that they are different
    private final List<String> list;
    private static final int LIST_LENGTH_THRESHOLD = 3;

    public ListLoggingRecursiveAction(String name, List<String> list) {
        this.name = name;
        this.list = list;
    }

    /**
     * Task's logic should be implemented inside this method
     */
    @Override
    protected void compute() {
        // divide up into subtasks each with a sublist of the original if exceeds threshold
        if(list.size() > LIST_LENGTH_THRESHOLD)
            ForkJoinTask.invokeAll(divideIntoSubtasksRecursively());
        else
            processTask();

    }

    /**
     * This method defines a way to split up a task into subtasks that will return a List<ListLoggingRecursiveAction> which will
     * be called by ForkJoinTask.invokeAll()
     *
     * Rather than using logic like the .divideIntoSubtasks() method below to create a specific amount of subtasks, let's just split the list in two, and let each instance deal with continued task dividing if it needs to.
     */
    private List<ListLoggingRecursiveAction> divideIntoSubtasksRecursively() { // Correct way
        // This will be continued to be called by ListLoggingRecursiveAction instances until their list size is < LIST_LENGTH_THRESHOLD

        int n = list.size() / 2;

        List<String> sublist1 = list.subList(0, n);
        List<String> sublist2 = list.subList(n, list.size());

        return List.of(
                new ListLoggingRecursiveAction(UUID.randomUUID().toString(), sublist1),
                new ListLoggingRecursiveAction(UUID.randomUUID().toString(), sublist2)
        );
    }

    /**
     * The non-recursive way, which will only ever create the exact amount of subtasks needed once
     */
    private List<ListLoggingRecursiveAction> divideIntoSubtasks() { // Not so correct way
        List<ListLoggingRecursiveAction> subtasks = new ArrayList<>();
        List<List<String>> partitions = new ArrayList<>();

        for(int i = 0; i < list.size(); i += LIST_LENGTH_THRESHOLD) {
            partitions.add(list.subList(i, Math.min(i + LIST_LENGTH_THRESHOLD, list.size())));
        }

        // for every partition of the original list, let's create a new ListLoggingRecursiveAction that will handle it's designated list of length <= 3
        partitions.forEach(partition -> subtasks.add(new ListLoggingRecursiveAction("", partition)));
        return subtasks;
    }

    /**
     * The list size will be less than the threshold, so now we can complete the task
     */
    private void processTask() {
        // log list contents
        System.out.printf("ListLoggingRecursiveAction[%s] says %s.%n", name, list);
    }
}
