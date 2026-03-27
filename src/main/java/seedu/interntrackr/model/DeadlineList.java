package seedu.interntrackr.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeadlineList {

    private ArrayList<Deadline> deadlines;

    /**
     * Constructs an empty DeadlineList.
     */
    public DeadlineList() {
        this.deadlines = new ArrayList<>();
    }

    /**
     * Constructs an DeadlineList from an existing list.
     *
     * @param deadlines The preloaded list of deadlines.
     */
    public DeadlineList(ArrayList<Deadline> deadlines) {
        this.deadlines = deadlines;
    }

    /**
     * Adds a new deadline to the list.
     *
     * @param deadline The deadline to add.
     */
    public void addDeadline(Deadline deadline) {
        deadlines.add(deadline);
    }

    /**
     * Returns the number of deadlines in the list.
     *
     * @return The size of the list.
     */
    public int getSize() {
        return deadlines.size();
    }

    /**
     * Returns an unmodifiable view of all deadlines.
     *
     * @return An unmodifiable list of deadlines.
     */
    public List<Deadline> getDeadlines() {
        return Collections.unmodifiableList(deadlines);
    }
}
