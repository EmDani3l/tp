package seedu.interntrackr.command;

import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.storage.Storage;
import seedu.interntrackr.ui.Ui;
import seedu.interntrackr.exception.InternTrackrException;

import java.util.logging.Logger;

/**
 * Displays a summary of all tracked internship applications.
 */
public class OverviewCommand extends Command {
    private static final Logger logger = Logger.getLogger(OverviewCommand.class.getName());

    /**
     * Executes the overview command by printing the total number of tracked applications.
     *
     * @param applications The current list of applications; must not be null.
     * @param ui           The UI used to display output; must not be null.
     * @param storage      Not used by this command; may be null.
     * @throws InternTrackrException Not thrown by this command, declared for interface compliance.
     */
    @Override
    public void execute(ApplicationList applications, Ui ui, Storage storage) throws InternTrackrException {
        assert applications != null : "ApplicationList must not be null";
        assert ui != null : "Ui must not be null";

        int size = applications.getSize();
        assert size >= 0 : "Application count must be non-negative";

        logger.info("Executing OverviewCommand. Application count: " + size);

        ui.showMessage("Overview:");
        ui.showMessage("You are currently tracking " + size + " applications.");
        ui.showMessage("Keep up the momentum!");

        logger.fine("OverviewCommand executed successfully.");
    }
}
