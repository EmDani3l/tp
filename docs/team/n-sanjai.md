# Navaneethan Sanjai - Project Portfolio Page

## Overview
**InternTrackr** is a CLI-first internship application manager for university students applying to multiple internships. It helps users track applications, statuses, contacts, offers, and deadlines in one place, instead of juggling spreadsheets, notes, and emails.

## Summary of Contributions

### Code Contributed
[RepoSense Dashboard](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=n-sanjai&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

### Enhancements Implemented
I implemented several user-facing and infrastructure-level enhancements across the project. These contributions span both core architecture and feature work.

**Core architecture and application flow**
- Implemented the main application controller in `InternTrackr`, including startup, the read-parse-execute loop, graceful error handling, and fallback to an empty list when stored data is missing or corrupted.
- Designed the control flow so command errors are caught and shown to the user without crashing the application.

**`overview` command**
- Implemented the `overview` command to show a quantitative summary of internship applications.
- Aggregated active application statuses using a `LinkedHashMap` so the displayed order remains stable and meaningful.
- Explicitly excluded archived applications from the breakdown and labelled the output as **Active Status Breakdown** to avoid ambiguity.

**`offer` command**
- Implemented the `offer` command for salary tracking.
- Added logic to update the salary of an application and automatically normalize its status to `"Offered"` when needed.
- Ensured salary updates are persisted immediately via `Storage#save()`.

**`contact` command**
- Implemented the `contact` command so users can store recruiter details directly inside an application.
- Added index validation and persistence support so the data remains available across sessions.

**`help` command**
- Implemented the `help` command to direct users to the full online User Guide instead of embedding large help text inside the CLI.
- This keeps the application lightweight while allowing documentation to stay up to date.

**Advanced deadline management**
- Implemented `deadline undone` and `deadline delete` so users can fully manage deadline lifecycle states.
- Updated `DeadlineCommandParser` to support the new subcommands and handle invalid indices cleanly.
- Added checks to prevent redundant operations such as unmarking an already incomplete deadline.

**Strict date validation**
- Strengthened deadline parsing by replacing Java’s default lenient behavior with `ResolverStyle.STRICT` and `uuuu`.
- Added validation to reject invalid calendar dates and past dates.
- This fixed a real correctness issue where invalid dates could otherwise be silently rewritten.

**List output abstraction**
- Implemented `toSummaryString()` in `Application` to improve the output of list-based commands.
- Instead of showing raw internal deadline data, list views now display clean summaries such as `Deadlines: 2 deadlines`.
- Integrated this abstraction into `list`, `list archive`, `filter`, and `find` for consistent UI behavior.

---
### Contributions to the User Guide
I contributed the UG documentation for:
- `contact`
- `offer`
- `overview`
- `help`
- `deadline undone`
- `deadline delete`

I also updated command formats, examples, and parameter constraints, and helped keep the command summary aligned with the implemented behavior.

---
### Contributions to the Developer Guide
I authored and updated DG sections covering:
- Main control flow and architecture
- UI component responsibilities
- `overview` feature implementation
- `offer` feature implementation
- `contact` feature implementation
- `help` feature implementation
- Advanced deadline management (`undone` and `delete`)
- Strict date validation
- UI list summary abstraction
- Design considerations for deadline validation, undone behavior, empty overview state, and uniform dependency passing

I also added and refined multiple UML diagrams, including:
- Startup sequence diagram
- Run loop happy-path sequence diagram
- Run loop error-path sequence diagram
- Overview command sequence diagram
- Overview runtime object diagram
- Offer command sequence diagram
- Contact command sequence diagram
- Help command sequence diagram

### Contributions to Team-Based Tasks
- Reviewed and corrected sequence diagrams whose message directions did not match the actual call flow.
- Standardized diagram style by applying activation bars more consistently.
- Corrected inaccurate DG design rationale related to dependency passing to read-only commands.
- Helped fix PE-D issues related to deadline, error handling, and list output formatting.

### Review and Mentoring Contributions
- Helped audit documentation and diagrams for technical correctness and consistency.
- Identified mismatches between implementation and documentation, especially in control flow and deadline-related behavior.

### Contributions Beyond the Project Team
- Contributed bug fixes discovered during PE-D, especially around invalid deadline dates, inconsistent deadline error handling, and raw internal deadline output in list views.
- These fixes improved both correctness and user experience in the final product.

---

## Contributions to the User Guide (Extracts)

### Adding recruiter contact details: `contact`
Stores the recruiter’s name and email for a specific application.

**Format:** `contact INDEX c/NAME e/EMAIL`

**Example:** `contact 1 c/John Doe e/john.doe@example.com`

### Logging an offer: `offer`
Stores the salary for an application and updates the application status to `Offered` if needed.

**Format:** `offer INDEX s/SALARY`

**Example:** `offer 1 s/5000.00`

### Unmarking a deadline as done: `deadline undone`
Marks a completed deadline as not done.

**Format:** `deadline undone INDEX i/DEADLINE_INDEX`

**Example:** `deadline undone 1 i/1`

### Deleting a deadline: `deadline delete`
Deletes a specific deadline from an application.

**Format:** `deadline delete INDEX i/DEADLINE_INDEX`

**Example:** `deadline delete 1 i/2`

---

## [Optional] Contributions to the Developer Guide (Extracts)

### Advanced Deadline Management
The application’s deadline capabilities were expanded to allow users to fully manage the state of their tasks by unmarking completed deadlines or deleting them entirely.

1. The `DeadlineCommandParser` was updated to securely parse the `undone` and `delete` subcommands, intercepting invalid non-numerical indices and preventing stack trace leaks.
2. `DeadlineUndoneCommand` and `DeadlineDeleteCommand` first resolve the active application, then retrieve its `DeadlineList`.
3. Strict bounds-checking is applied to both the application index and the deadline index.
4. For `undone`, the command throws an exception if the deadline is already incomplete, preventing redundant operations and unnecessary saves.
5. For `delete`, the command removes the selected deadline and persists the change immediately.

### Strict Date Validation
To ensure data integrity for internship timelines, the date parsing logic for deadlines was overhauled to prevent silent mutation of invalid dates.

By default, Java’s `DateTimeFormatter` uses `ResolverStyle.SMART`, which may auto-correct invalid dates. The parser was upgraded to use `ResolverStyle.STRICT` with the `uuuu` year format, and additional checks reject past dates. If a user enters a non-existent calendar date or past date, the parser throws an `InternTrackrException` immediately.

### UI List Summary Abstraction (`toSummaryString`)
To prevent CLI clutter when applications accumulate many deadlines, a new `toSummaryString()` abstraction was introduced.

Instead of relying on `toString()`, which exposes raw internal deadline data, `toSummaryString()` computes the deadline count and formats it cleanly, such as `Deadlines: 0 deadlines` or `Deadlines: 2 deadlines`. This abstraction is used consistently across `ListCommand`, `ListArchiveCommand`, `FilterCommand`, and `FindCommand`.
