# Emry Daniel - Project Portfolio Page

## Overview
**InternTrackr** is a centralized CLI-based internship management system designed for students navigating the peak recruitment season. It provides a streamlined interface to track application statuses, manage upcoming deadlines, and store recruiter contacts, ensuring that no opportunity is lost in a sea of emails.

## Summary of Contributions

### 1. New Features
* **Status Management (`status` command)**: Developed the logic to update application stages (e.g., *Applied* to *Interview*). This included strict validation against a predefined whitelist of statuses to ensure data integrity for downstream features like `overview`.
* **Advanced Filtering (`filter` command)**: Implemented a filtering engine that allows users to isolate applications by their current stage. Included a "reset" functionality (`filter clear`) to quickly return to the global view.
* **Search Functionality (`find` command)**: Created a keyword search feature that performs case-insensitive partial matching across both company names and roles, significantly reducing search time for power users.
* **Safety Confirmation (`clear` command)**: Integrated a destructive command safety net that requires an interactive "yes" confirmation before wiping the local database, preventing accidental data loss.

### 2. Code Contributed
* [Link to RepoSense Dashboard] *(https://nus-cs2113-ay2526-s2.github.io/tp-dashboard/?search=emdani3l&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2026-02-20T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)*

### 3. Contributions to the User Guide (Extracts)

> #### **Updating application status: `status`**
> Updates the status of an existing application at the given index.
> **Format:** `status INDEX s/STATUS`
> * **Example:** `status 1 s/"Interview"`
>
> #### **Filtering by status: `filter`**
> Shows only applications matching a specific recruitment stage.
> **Format:** `filter s/STATUS`
> * **Example:** `filter s/"Pending"`
>
> #### **Finding applications: `find`**
> Finds applications whose company name or role contains a specified keyword.
> **Format:** `find KEYWORD`
> * **Example:** `find Shopee`
>
> #### **Clearing all data: `clear`**
> Wipes all applications from the list. Requires an explicit `yes` confirmation.
> **Format:** `clear`

### 4. Contributions to the Developer Guide (Extracts)

> #### **Status and Filter Implementation**
> These features utilize a **normalization engine** to provide a resilient user experience. When a user inputs a status, the system invokes `Application#getNormalizedStatus()` to convert it to a canonical format, ensuring consistent matching for the `filter` command and accurate data aggregation for the `overview` feature.
>
> #### **Find Feature Implementation**
> The `find` command performs a partial-match search across both **Company** and **Role** fields. The logic iterates through the `ApplicationList` and uses lowercase comparison to maintain search flexibility, allowing users to find positions by role type (e.g., "Frontend") or firm name.
>
> #### **Clear Feature Implementation**
> To prevent catastrophic data loss, the `clear` command implements a **two-step confirmation workflow**. The `ClearCommand#execute()` method triggers a blocking UI prompt that waits for a specific "yes" input before invoking `applications.clear()` and permanently overwriting the local storage file.

---
