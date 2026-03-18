package seedu.interntrackr.parser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Logger;

import seedu.interntrackr.command.AddCommand;
import seedu.interntrackr.command.Command;
import seedu.interntrackr.command.DeadlineCommand;
import seedu.interntrackr.command.DeleteCommand;
import seedu.interntrackr.command.ExitCommand;
import seedu.interntrackr.command.FilterCommand;
import seedu.interntrackr.command.ListCommand;
import seedu.interntrackr.command.OverviewCommand;
import seedu.interntrackr.command.StatusCommand;
import seedu.interntrackr.exception.InternTrackrException;

/**
 * Parses raw user input into executable command objects.
 */
public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    public static Command parse(String fullCommand) throws InternTrackrException {
        assert fullCommand != null : "Input command must not be null";

        if (fullCommand == null || fullCommand.isBlank()) {
            logger.warning("parse() received null or blank input.");
            throw new InternTrackrException("Input cannot be empty. Please enter a command.");
        }

        String[] parts = fullCommand.trim().split(" ", 2);
        String commandWord = parts[0].toLowerCase();
        String arguments = parts.length > 1 ? parts[1].trim() : "";

        logger.fine("Parsing command: \"" + commandWord + "\" with args: \"" + arguments + "\"");

        switch (commandWord) {
        case "add":
            if (!arguments.contains("c/") || !arguments.contains("r/")) {
                logger.warning("Add command missing c/ or r/ parameter.");
                throw new InternTrackrException("Invalid format. Usage: add c/COMPANY r/ROLE");
            }
            return parseAddCommand(arguments);

        case "overview":
            logger.fine("Parsed: OverviewCommand");
            return new OverviewCommand();

        case "list":
            logger.fine("Parsed: ListCommand");
            return new ListCommand();

        case "status":
            if (!arguments.contains(" s/")) {
                logger.warning("Status command missing s/ parameter.");
                throw new InternTrackrException("Invalid format. Usage: status INDEX s/STATUS");
            }
            String[] statusArgs = arguments.split(" s/", 2);
            try {
                int index = Integer.parseInt(statusArgs[0].trim());
                String parsedStatus = statusArgs[1].replace("\"", "").trim();
                logger.fine("Parsed: StatusCommand index=" + index + " status=" + parsedStatus);
                return new StatusCommand(index, parsedStatus);
            } catch (NumberFormatException e) {
                logger.warning("Status index is not a number: \"" + statusArgs[0].trim() + "\"");
                throw new InternTrackrException("The application index must be a number.");
            }

        case "delete":
            if (arguments.isEmpty()) {
                logger.warning("Delete command missing index.");
                throw new InternTrackrException("Invalid format. Usage: delete INDEX");
            }
            try {
                int deleteIndex = Integer.parseInt(arguments.trim());
                if (deleteIndex <= 0) {
                    logger.warning("Delete index is non-positive: " + deleteIndex);
                    throw new InternTrackrException("Index must be a positive integer.");
                }
                logger.fine("Parsed: DeleteCommand index=" + deleteIndex);
                return new DeleteCommand(deleteIndex);
            } catch (NumberFormatException e) {
                logger.warning("Delete index is not a number: \"" + arguments.trim() + "\"");
                throw new InternTrackrException("The application index must be a number.");
            }

        case "filter":
            if (arguments.isEmpty()) {
                logger.warning("Filter command missing arguments.");
                throw new InternTrackrException("Invalid format. Usage: filter s/STATUS or filter clear");
            }
            if (arguments.equalsIgnoreCase("clear")) {
                logger.fine("Parsed: FilterCommand (clear)");
                return new FilterCommand(true);
            }
            if (!arguments.startsWith("s/")) {
                logger.warning("Filter command missing s/ prefix.");
                throw new InternTrackrException("Invalid format. Usage: filter s/STATUS");
            }
            String filterStatus = arguments.substring(2).replace("\"", "").trim();
            logger.fine("Parsed: FilterCommand status=" + filterStatus);
            return new FilterCommand(filterStatus);

        case "deadline":
            if (arguments.startsWith("add ")) {
                String subArgs = arguments.substring(4).trim();
                if (!subArgs.contains(" t/") || !subArgs.contains(" d/")) {
                    logger.warning("Deadline add command missing t/ or d/ parameter.");
                    throw new InternTrackrException(
                            "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY [n/NOTES]");
                }
                try {
                    int typeIndex = subArgs.indexOf(" t/");
                    int dateIndex = subArgs.indexOf(" d/");
                    if (typeIndex == -1 || dateIndex == -1 || typeIndex > dateIndex) {
                        logger.warning("Deadline add command has incorrect parameter ordering.");
                        throw new InternTrackrException(
                                "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY");
                    }

                    int index = Integer.parseInt(subArgs.substring(0, typeIndex).trim());
                    String deadlineType = subArgs.substring(typeIndex + 3, dateIndex).trim().replace("\"", "");
                    String dueDateStr = getDueDateStr(subArgs, dateIndex, deadlineType);

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate dueDate = LocalDate.parse(dueDateStr, formatter);

                    logger.fine("Parsed: DeadlineCommand index=" + index + " type=" + deadlineType);
                    return new DeadlineCommand(index, deadlineType, dueDate);

                } catch (NumberFormatException e) {
                    logger.warning("Deadline index is not a number.");
                    throw new InternTrackrException("The application index must be a number.");
                } catch (DateTimeParseException e) {
                    logger.warning("Deadline date format invalid.");
                    throw new InternTrackrException("Invalid date format. Use DD-MM-YYYY.");
                }
            } else {
                logger.warning("Deadline command missing 'add' subcommand.");
                throw new InternTrackrException(
                        "Invalid format. Usage: deadline add INDEX t/TYPE d/DD-MM-YYYY [n/NOTES]");
            }

        case "exit":
            logger.fine("Parsed: ExitCommand");
            return new ExitCommand();

        default:
            logger.warning("Unknown command: \"" + commandWord + "\"");
            throw new InternTrackrException("I'm sorry, but I don't know what that command means :-(");
        }
    }

    private static String getDueDateStr(String subArgs, int dateIndex, String deadlineType)
            throws InternTrackrException {
        assert subArgs != null : "subArgs must not be null";
        assert dateIndex >= 0 : "dateIndex must be non-negative";

        String dueDateStr = subArgs.substring(dateIndex + 3).trim().replace("\"", "");

        int notesIndex = dueDateStr.indexOf(" n/");
        if (notesIndex != -1) {
            dueDateStr = dueDateStr.substring(0, notesIndex).trim();
        }

        if (deadlineType.isEmpty() || dueDateStr.isEmpty()) {
            logger.warning("Deadline type or due date is empty.");
            throw new InternTrackrException("Deadline type and due date cannot be empty.");
        }
        return dueDateStr;
    }

    private static AddCommand parseAddCommand(String arguments) throws InternTrackrException {
        assert arguments != null : "arguments must not be null";

        logger.fine("Parsing add command args: \"" + arguments + "\"");

        String company = "";
        String role = "";

        try {
            int cIndex = arguments.indexOf("c/");
            int rIndex = arguments.indexOf("r/");

            if (cIndex < rIndex) {
                company = arguments.substring(cIndex + 2, rIndex).trim().replace("\"", "");
                role = arguments.substring(rIndex + 2).trim().replace("\"", "");
            } else {
                role = arguments.substring(rIndex + 2, cIndex).trim().replace("\"", "");
                company = arguments.substring(cIndex + 2).trim().replace("\"", "");
            }

            if (company.isEmpty() || role.isEmpty()) {
                logger.warning("Add command has blank company or role after parsing.");
                throw new InternTrackrException("Company and role cannot be empty.");
            }

            logger.fine("Parsed AddCommand: company=\"" + company + "\", role=\"" + role + "\"");
            return new AddCommand(company, role);
        } catch (InternTrackrException e) {
            throw e; // re-throw known exceptions without wrapping
        } catch (Exception e) {
            logger.warning("Unexpected error parsing add command: " + e.getMessage());
            throw new InternTrackrException("Error parsing add command.");
        }
    }
}
