package seedu.interntrackr.command;

import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Filters applications based on status.
 */
public class FilterCommand extends Command {
    private String status;

    public FilterCommand(String status) {
        this.status = status;
    }

    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        // TODO: Filter applications by status and display results
        System.out.println("Here are the applications with status: " + this.status);
        int matchCount = 0;

        for (int i = 0; i < applications.getSize(); i++) {
            Application app = applications.getApplication(i);
            if (app.getStatus().equalsIgnoreCase(this.status)) {
                System.out.println((i + 1) + ". " + app.toString());
                matchCount++;
            }
        }

        if (matchCount == 0) {
            System.out.println("No applications found with status: " + this.status);
        }
    }
}
