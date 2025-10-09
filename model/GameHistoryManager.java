package model;


import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;




public class GameHistoryManager {


    private static final String FILE_NAME = "tictactoe_history.txt";
    private final Path filePath;


    public GameHistoryManager() {
        // project root = current working directory when you click Run above main()
        this.filePath = Paths.get(FILE_NAME);
        ensureFile();
    }


    private void ensureFile() {
        try {
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void appendRecord(String mode, String winnerLabel) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        String line = String.format("%s | %s | Winner: %s", timestamp, mode, winnerLabel);


        try (BufferedWriter bw = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /** Returns lines most-recent-first. If file empty, returns empty list. */
    public java.util.List<String> readAllDescending() {
        try {
            java.util.List<String> lines = Files.readAllLines(filePath);
            // Sort by timestamp descending assuming ISO-like prefix
            Collections.reverse(lines); // newer appended last -> reverse for most-recent-first
            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}




