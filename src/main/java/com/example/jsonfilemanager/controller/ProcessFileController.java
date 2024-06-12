package com.example.jsonfilemanager.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessFileController {

    private static final String JSON_FILES_DIRECTORY = "D:\\Faculdade\\pos\\Dev Back_end\\jsonfilemanager\\json_files";
    private static final String PROCESSED_FILES_RECORD = JSON_FILES_DIRECTORY + "\\processed_files.txt";
    private int currentIndex = 0;

    @GetMapping("/get_next_file")
    public String getNextFile() {
        File folder = new File(JSON_FILES_DIRECTORY);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            return "No files in the queue!";
        }

        // Load processed files
        Set<String> processedFiles = loadProcessedFiles();

        for (File file : files) {
            if (!processedFiles.contains(file.getName())) {
                try {
                    List<String> lines = Files.readAllLines(file.toPath());
                    StringBuilder content = new StringBuilder();
                    for (String line : lines) {
                        content.append(line);
                    }
                    markFileAsProcessed(file.getName());
                    return content.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Error reading file.";
                }
            }
        }
        return "No files in the queue!";
    }

    private Set<String> loadProcessedFiles() {
        Set<String> processedFiles = new HashSet<>();
        File recordFile = new File(PROCESSED_FILES_RECORD);
        if (recordFile.exists()) {
            try {
                List<String> lines = Files.readAllLines(recordFile.toPath());
                processedFiles.addAll(lines);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return processedFiles;
    }

    private void markFileAsProcessed(String fileName) {
        File recordFile = new File(PROCESSED_FILES_RECORD);
        try {
            Files.write(recordFile.toPath(), (fileName + System.lineSeparator()).getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

} 

//     private int currentIndex = 0;

//     @GetMapping("/get_next_file")
//     public String getNextFile() {
//         File folder = new File(".");
//         File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        
//         if (files != null && currentIndex < files.length) {
//             try {
//                 List<String> lines = Files.readAllLines(files[currentIndex++].toPath());
//                 StringBuilder content = new StringBuilder();
//                 for (String line : lines) {
//                     content.append(line);
//                 }
//                 return content.toString();
//             } catch (IOException e) {
//                 e.printStackTrace();
//                 return "Error reading file.";
//             }
//         } else {
//             return "No files in the queue!";
//         }
//     }
// }
