package com.example.jsonfilemanager.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendFileController {

    // Define o diretório diretamente no código
    private static final String JSON_FILES_DIRECTORY = "D:\\Faculdade\\pos\\Dev Back_end\\jsonfilemanager\\json_files";

    @PostMapping("/send_file")
    public String sendFile(@RequestBody String jsonData) {
        try {
            // Verifica se o diretório existe, se não, cria o diretório
            File directory = new File(JSON_FILES_DIRECTORY);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Cria o nome do arquivo com base na data e hora atual
            String fileName = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date()) + ".json";
            
            // Especifica o caminho completo do arquivo
            FileWriter fileWriter = new FileWriter(new File(directory, fileName));
            fileWriter.write(jsonData);
            fileWriter.close();
            return "Arquivo salvo com sucesso!";
        } catch (IOException e) {
            e.printStackTrace();
            return "Erro ao salvar arquivo.";
        }
    }
}
