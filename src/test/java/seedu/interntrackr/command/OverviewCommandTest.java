package seedu.interntrackr.command;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.interntrackr.model.Application;
import seedu.interntrackr.model.ApplicationList;
import seedu.interntrackr.ui.Ui;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OverviewCommandTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setIn(new ByteArrayInputStream("".getBytes()));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void execute_validList_printsTotalCountAndBreakdown() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Google", "Software Engineer", "Applied"));
        applications.addApplication(new Application("Meta", "Data Scientist", "Interview"));
        applications.addApplication(new Application("Apple", "Product Manager", "Applied"));

        Ui ui = new Ui();
        new OverviewCommand().execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("tracking 3 application(s)"), "Should show tracking count of 3");
        assertTrue(output.contains("Status Breakdown:"), "Should show the breakdown header");
        assertTrue(output.contains("- Applied: 2"), "Should tally 2 Applied");
        assertTrue(output.contains("- Interview: 1"), "Should tally 1 Interview");
        assertFalse(output.contains("- Rejected:"), "Should not display statuses with 0 count");
        assertTrue(output.contains("Keep up the momentum!"), "Should show encouragement message");
    }

    @Test
    public void execute_singleApplication_showsCountOfOne() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("Grab", "Data Analyst", "Offered"));

        Ui ui = new Ui();
        new OverviewCommand().execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("tracking 1 application(s)"), "Should show tracking count of 1");
        assertTrue(output.contains("- Offered: 1"), "Should tally 1 Offered");
    }

    @Test
    public void execute_emptyList_showsCustomEmptyMessage() {
        ApplicationList applications = new ApplicationList();
        Ui ui = new Ui();
        new OverviewCommand().execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("You haven't tracked any applications yet. Start adding some!"),
                "Should show custom empty list message");
        assertFalse(output.contains("tracking 0 application(s)"), "Should not show zero count");
        assertFalse(output.contains("Status Breakdown:"), "Should not show breakdown for empty list");
        assertFalse(output.contains("Keep up the momentum!"), "Should not show momentum message for empty list");
    }

    @Test
    public void execute_emptyList_showsOverviewHeader() {
        ApplicationList applications = new ApplicationList();
        Ui ui = new Ui();
        new OverviewCommand().execute(applications, ui, null);

        assertTrue(outContent.toString().contains("Overview:"), "Should always print 'Overview:' header");
    }

    @Test
    public void execute_nullApplications_throwsAssertionError() {
        Ui ui = new Ui();
        assertThrows(AssertionError.class,
                () -> new OverviewCommand().execute(null, ui, null),
                "Should throw AssertionError when ApplicationList is null");
    }

    @Test
    public void execute_nullUi_throwsAssertionError() {
        ApplicationList applications = new ApplicationList();
        assertThrows(AssertionError.class,
                () -> new OverviewCommand().execute(applications, null, null),
                "Should throw AssertionError when Ui is null");
    }

    @Test
    public void execute_nullStorage_doesNotThrow() {
        ApplicationList applications = new ApplicationList();
        applications.addApplication(new Application("ByteDance", "SWE Intern", "Applied"));
        Ui ui = new Ui();

        new OverviewCommand().execute(applications, ui, null);

        String output = outContent.toString();
        assertTrue(output.contains("tracking 1 application(s)"));
        assertTrue(output.contains("- Applied: 1"));
    }
}
