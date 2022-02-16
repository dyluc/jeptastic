package loom;

import loom.prerequisites.ListLoggingRecursiveAction;

import java.util.ArrayList;
import java.util.List;

public class ProjectLoom {

    /**
     * https://cr.openjdk.java.net/~rpressler/loom/Loom-Proposal.html
     *
     * The new concurrency model coming to a JDK near you: Fibers!
     *
     * Project Loom is a big happening at Oracle at the moment to introduce a more lightweight and efficient thread. Managed internally by the JVM, the same abstractions used
     * by developers will now offer better performance with less thread-related overhead.
     *
     * A fiber will be made up of two components, a continuation and a scheduler. As it stands, the existing ForkJoinPool scheduler is thought to serve (by Oracle) as
     * a very good fiber scheduler, so there doesn't seem there is any intention to redesign a new scheduler for this new concurrent construct. There is an intention, outlined within
     * the project's proposal, to create a new feature called unwind-and-invoke, or UAI, which will be a lightweight construct to manipulate call stacks inside the JVM by "unwinding" the stack
     * and invoking a method with a particular set of arguments. This is quite an interesting and complicated feature, but will be a requirement for the project.
     *
     */
    public ProjectLoom() {


    }

    public static void main(String[] args) {
        new ProjectLoom();
    }
}
