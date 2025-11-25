
/*
 * Case_ID | Player_ID | Activity | Timestamp | Category | Question_Value | Answer_Given | Result | Score_After_Play
 */
package com.group_project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileObserver implements Observer {

    private final Path logPath;

    public FileObserver(String fileName) {
        this.logPath = Paths.get(fileName);
        // Write header if file does not exist
        if (!Files.exists(this.logPath)) {
            String header = "Case_ID | Player_ID | Activity | Timestamp | Category | Question_Value | Answer_Given | Result | Score_After_Play" + System.lineSeparator();
            try {
                Files.write(this.logPath, header.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            } catch (IOException e) {
                System.err.println("Failed to write header to log file: " + e.getMessage());
            }
        }
    }

    @Override
    public void update(String message) {
        // Message is expected to be already formatted by Logger (includes timestamp)
        String line = message + System.lineSeparator();
        try {
            Files.write(logPath, line.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}
