# InternTrackr

InternTrackr is a CLI-first internship application manager for university students applying to many internships. It helps you track where you applied, current status, contacts, offers, deadlines, and notes in one place — instead of juggling spreadsheets, emails, and Notion pages.

## Setting up in IntelliJ

Prerequisites: JDK 17 (use the exact version), update IntelliJ to the most recent version.

1. **Ensure IntelliJ JDK 17 is defined as an SDK**, as described [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk) -- this step is not needed if you have used JDK 17 in a previous IntelliJ project.
2. **Import the project _as a Gradle project_**, as described [here](https://se-education.org/guides/tutorials/intellijImportGradleProject.html).
3. **Verify the setup**: After the importing is complete, locate the `src/main/java/seedu/interntrackr/InternTrackr.java` file, right-click it, and choose `Run InternTrackr.main()`. If the setup is correct, you should see something like the below:
   ```
   > Task :compileJava
   > Task :processResources NO-SOURCE
   > Task :classes

   > Task :InternTrackr.main()
   ____________________________________________________________
   Welcome to InternTrackr! Ready to hunt for some internships?
   ____________________________________________________________
   ```
   Type a command (e.g. `help`) and press Enter.

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Build automation using Gradle

* This project uses Gradle for build automation and dependency management. It includes a basic build script as well (i.e. the `build.gradle` file).
* If you are new to Gradle, refer to the [Gradle Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/gradle.html).

## Testing

### I/O redirection tests

* To run _I/O redirection_ tests (aka _Text UI tests_), navigate to the `text-ui-test` and run the `runtest(.bat/.sh)` script.

### JUnit tests

* If you are new to JUnit, refer to the [JUnit Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/junit.html).

## Checkstyle

* A sample CheckStyle rule configuration is provided in this project.
* If you are new to Checkstyle, refer to the [Checkstyle Tutorial at se-education.org/guides](https://se-education.org/guides/tutorials/checkstyle.html).

## CI using GitHub Actions

The project uses [GitHub actions](https://github.com/features/actions) for CI. When you push a commit to this repo or PR against it, GitHub actions will run automatically to build and verify the code as updated by the commit/PR.

## Documentation

`/docs` folder contains the project documentation.

* [User Guide](docs/UserGuide.md)
* [Developer Guide](docs/DeveloperGuide.md)
