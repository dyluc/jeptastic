package loom.prerequisites;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * Sums all numbers in an int list and returns the result. This class represents a RecursiveTask which accepts a list of integers, defining a method to divide the list if a threshold is exceeded
 * and a method to sum the contents of a list.
 */
public class ListSummingRecursiveTask extends RecursiveTask<Integer> {

    private final String name; // random identifier for logger just to see that they are different
    private final int[] array;
    private static final int LIST_LENGTH_THRESHOLD = 3;

    public ListSummingRecursiveTask(String name, int[] array) {
        this.name = name;
        this.array = array;
    }

    @Override
    protected Integer compute() {
        // divide up into subtasks each with a sublist of the original if exceeds threshold
        if(array.length > LIST_LENGTH_THRESHOLD) {
            // ForkJoinTask#invokeAll is a convenience method to submit multiple ForkJoinTask instances at once with convenient method parameters.
            // fork() and join() can also be used separately to accomplish the same thing to submit a task(fork) and then execute it(join). In a RecursiveAction, join returns null
            Collection<ListSummingRecursiveTask> listSummingRecursiveTasks = ForkJoinTask.invokeAll(divideIntoSubtasksRecursively()); // collection of future objects returned after subtasks submitted to common pool
            return listSummingRecursiveTasks.stream().mapToInt(ForkJoinTask::join).sum(); // ForkJoinTask#join will return the result of the computation when Future#isDone is true, then all task results are summed and returned.
        } else
            return processTask();
    }

    private List<ListSummingRecursiveTask> divideIntoSubtasksRecursively() {
        // This will be continued to be called by ListSummingRecursiveTask instances until their list size is < LIST_LENGTH_THRESHOLD

        int n = array.length / 2;

        int[] subArray1 = Arrays.copyOfRange(array, 0, n);
        int[] subArray2 = Arrays.copyOfRange(array, n, array.length);

        return List.of(
                new ListSummingRecursiveTask(UUID.randomUUID().toString(), subArray1),
                new ListSummingRecursiveTask(UUID.randomUUID().toString(), subArray2)
        );
    }

    private Integer processTask() {
        // log list contents
        System.out.printf("ListSummingRecursiveTask[%s] is summing up %s.%n", name, Arrays.toString(array));
        return Arrays.stream(array).sum();
    }
}
