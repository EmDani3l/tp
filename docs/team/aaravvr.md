# Aarav Rajesh - Project Portfolio Page

## Overview

**InternTrackr** is a CLI-first internship application manager for university students applying to multiple internships. It helps users track where they applied, current statuses, and important dates in one place so they do not miss deadlines or lose track across spreadsheets, notes, and emails.

## Summary of Contributions

### 1. New Features

* **Adding applications (`add`)**: Implemented the `add` command to create internship applications with flexible prefix ordering (`c/` and `r/` in either order), duplicate detection, and immediate persistence.

* **Deleting applications (`delete`)**: Implemented deletion for both active and archived applications. `delete INDEX` removes active entries, while `delete archive INDEX` allows archived entries to be removed directly without first unarchiving them.

* **Archiving applications (`archive`)**: Implemented the `archive` command to hide inactive applications from the default list without deleting them, preserving application history while reducing clutter in the active view.

* **Listing archived applications (`list archive`)**: Implemented a separate archived-applications view with its own archived-only index, allowing hidden applications to be reviewed independently from active ones.

* **Restoring archived applications (`unarchive`)**: Implemented the `unarchive` command so archived applications can be restored to the active list using the index shown in `list archive`.

### 2. Code Contributed

* [RepoSense link](https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=aaravvr&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2026-02-20T00%3A00%3A00&filteredFileName=)

### 3. Other Enhancements and Fixes

* Refactored `Parser.java` into a centralized dispatcher backed by dedicated parser classes such as `AddCommandParser`, `DeleteCommandParser`, `ArchiveCommandParser`, and `UnarchiveCommandParser`.
* Added `getActiveApplication(int displayIndex)` and `getArchivedApplication(int displayIndex)` so index-based commands resolve against the correct visible view.
* Encoded archived state as `| archived:true` to avoid ambiguity with deadline `isDone` fields in storage.

### 4. Contributions to the User Guide

Contributed UG sections for `add`, `delete`, `archive`, `unarchive`, and `list archive`, documenting command formats, active vs archived index behavior, and the difference between hiding and permanently deleting applications.

### 5. Contributions to the Developer Guide

Contributed DG sections for the Add, Delete, Parser, Archive, List Archive, and Unarchive features. I also created the UML diagrams `AaravAddCommandSequence`, `AaravDeleteCommandSequence`, `AaravArchiveCommandSequence`, `AaravUnarchiveCommandSequence`, `AaravListArchiveCommandSequence`, `AaravParserSequence`, and `AaravClassDiagram`.

### 6. Contributions to Team-Based Tasks

Helped maintain consistency of archive-related behavior across parsing, command execution, list rendering, storage, and documentation, and helped tackle PE dry run related bugs involving command behavior, indexing consistency, and documentation accuracy.

### 7. Review and Coordination Contributions

* [PR #45](https://github.com/AY2526S2-CS2113-T14-1/tp/pull/45) — Reviewed Emry's defensive coding additions for `FilterCommand` and `StatusCommand`.
* [PR #51](https://github.com/AY2526S2-CS2113-T14-1/tp/pull/51) — Reviewed and merged Eugenia's defensiveness improvements to `Deadline` and `DeadlineCommand`.
* [PR #77](https://github.com/AY2526S2-CS2113-T14-1/tp/pull/77) — Reviewed Sanjai's test coverage additions for `InternTrackr`, `Ui`, and `OverviewCommand`.
* [PR #84](https://github.com/AY2526S2-CS2113-T14-1/tp/pull/84) — Reviewed Sanjai's DG additions covering product scope, user stories, and NFRs.

### 8. Contributions Beyond the Project Team

* Reported bugs in another team's product during the Practical Exam Dry Run, including issues with missing input validation and inconsistent command behavior.

---

## Contributions to the User Guide (Extracts)

### Archiving an application: `archive`

Archives an internship application so it no longer appears in the default active application list.

**Format:** `archive INDEX`

- Archives the application at the given `INDEX`.
- The index refers to the index number shown in the default application list.
- `INDEX` must be a positive integer 1, 2, 3, ...
- Archived applications are not deleted. They are hidden from the default `list` view and can still be viewed using `list archive`.

**Examples:** `archive 1`, `archive 3`

---

### Deleting an application: `delete`

Deletes the specified active application, or optionally an archived application directly.

**Format:** `delete INDEX` or `delete archive INDEX`

- `delete INDEX` — deletes the active application at the given `INDEX`, as shown in the default `list` output.
- `delete archive INDEX` — deletes the archived application at the given `INDEX`, as shown in the `list archive` output.
- `INDEX` must be a positive integer 1, 2, 3, ...

**Examples:** `delete 2`, `delete archive 1`
