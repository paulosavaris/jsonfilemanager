package com.example.jsonfilemanager.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessFileController {


    private static final String JSON_FILES_DIRECTORY = "D:\\Faculdade\\pos\\Dev Back_end\\jsonfilemanager\\json_files";
    private static final String PROCESSED_FILES_RECORD = JSON_FILES_DIRECTORY + "\\processed_files.txt";

    @GetMapping("/get_next_file")
    public String getNextFile() {
        File folder = new File(JSON_FILES_DIRECTORY);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        if (files == null || files.length == 0) {
            return "Nenhum arquivo na fila!";
        }

        // Carregar arquivos processados
        Set<String> processedFiles = loadProcessedFiles();

        // Obtenha os arquivos restantes
        List<File> remainingFiles = getRemainingFiles(files, processedFiles);

        // Se não houver arquivos restantes
        if (remainingFiles.isEmpty()) {
            return "Nenhum arquivo na fila!";
        }

        File nextFile = remainingFiles.get(0);
        try {
            List<String> lines = Files.readAllLines(nextFile.toPath());
            StringBuilder content = new StringBuilder();
            for (String line : lines) {
                content.append(line);
            }
            markFileAsProcessed(nextFile.getName());

            // Carrega os arquivos processados ​​novamente para obter a lista atualizada
            processedFiles = loadProcessedFiles();

            // Atualizar os arquivos restantes
            remainingFiles = getRemainingFiles(files, processedFiles);

            return String.format("Conteudo do proximo arquivo:\n%s\n\nArquivos Processados: %s\n\nArquivos restantes: %s", 
                                 content.toString(), 
                                 processedFiles, 
                                 remainingFiles.stream().map(File::getName).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
            return "Error na leitura de arquivo.";
        }
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

    private List<File> getRemainingFiles(File[] files, Set<String> processedFiles) {
        return List.of(files).stream()
                             .filter(file -> !processedFiles.contains(file.getName()))
                             .collect(Collectors.toList());
    }
} 
