package com.example.jsonfilemanager.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class FileService {
    
    private static final String DIRECTORY_PATH = "json_files";
    private static final String PROCESSED_FILES = "processed_files.txt";

    public FileService() throws IOException {
        File directory = new File(DIRECTORY_PATH);
        if (!directory.exists()) {
            directory.mkdir();
        }
        File processedFile = new File(PROCESSED_FILES);
        if (!processedFile.exists()) {
            processedFile.createNewFile();
        }
    }

    public void saveFile(String content) throws IOException {
        String timestamp = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
        String fileName = DIRECTORY_PATH + "/" + timestamp + ".json";
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(content);
        fileWriter.close();
    }

    public String getNextFile() throws IOException {
        File directory = new File(DIRECTORY_PATH);
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            return null;
        }

        List<String> processedFiles = Files.readAllLines(Paths.get(PROCESSED_FILES));
        for (File file : files) {
            if (!processedFiles.contains(file.getName())) {
                String content = new String(Files.readAllBytes(file.toPath()));
                processedFiles.add(file.getName());
                Files.write(Paths.get(PROCESSED_FILES), String.join("\n", processedFiles).getBytes());
                return content;
            }
        }
        return null;
    }
}
