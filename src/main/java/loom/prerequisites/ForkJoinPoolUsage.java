package loom.prerequisites;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 *
 *
 * Since the existing ForkJoinPool seems to be the scheduler that Oracle are going with for this project, I figured I'd write a little about it and give some examples
 * before diving into the project itself.
 */
public class ForkJoinPoolUsage {




    /**
     * So what is the ForkJoinPool?
     *
     * A thread scheduler, part of the fork/join framework, which is aimed at improving parallel processing through more efficient use of all available processor cores. It's takes a
     * divide-and-conquer approach (similar to how quick sort works) to recursively split up processes into smaller, more manageable subtasks. The results of each subtask are recursively
     * joined into a single result (or void) when all subtasks have finished. The framework makes use of a pool of threads to accomplish this know as the ForkJoinPool, which will manage these
     * separate threads with type ForkJoinWorkerThread.
     *
     * The ForkJoinPool implements the ExecutorService interface. Rather than creating a new thread for each subtask, each thread in the pool has its own deque which stores tasks. This whole operation
     * makes use of the work-stealing algorithm where each thread will execute tasks from its deque head, and takes tasks from the tail of the deque of another busy thread or from a global entry queue when
     * empty. This workflow means larger workload chunks can be prioritised, there will be fewer threads competing for the same task, and a reduced number of times a thread goes looking for work.
     *
     */
    public static void ForkJoinPoolUsage() {

        // convenience method to instantiate a ForkJoinPool, getting access to the statically constructed common pool. Alternatively,
        // a static instance can be created using the constructor to accomplish the same thing.
        ForkJoinPool pool = ForkJoinPool.commonPool();

        // ForkJoinTasks<V> is the base type for tasks executed by the ForkJoinPool. This base class has 2 implementations; RecursiveAction (void task), and RecursiveTask<V> (task that returns a value)
        // One of these two subclasses should be extended depending on the nature of the task to be completed.

        // Submitting a RecursiveTask:
        // .execute() or .submit() will push the task to the work queue to be worked on later.
        int[] array = {4, 25, 8, 65, 24, 53, 45, 85};
        System.out.println(Arrays.toString(array));
        ListSummingRecursiveTask baseListSummingRecursiveTask = new ListSummingRecursiveTask("Base ListSummingRecursiveTask", array);
        pool.execute(baseListSummingRecursiveTask);
        int sum = baseListSummingRecursiveTask.join(); // same method called inside class to obtain the result of the operation

        // .invoke() will execute the task and join the result (does same thing as above)
//        sum = pool.invoke(baseListSummingRecursiveTask);

        System.out.println("Result of the operation is " + sum);

        // The same can be accomplished with RecursiveAction, except it doesn't return a result:
        List<String> list = List.of("The", "Quick", "Brown", "Fox", "Jumped", "Over", "The", "Lazy", "Dog");
        System.out.println(list);
        ListLoggingRecursiveAction baseListLoggingRecursiveAction = new ListLoggingRecursiveAction("Base ListLoggingRecursiveAction", list);
        pool.invoke(baseListLoggingRecursiveAction);

    }
}
