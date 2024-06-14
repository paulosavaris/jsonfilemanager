package com.example.jsonfilemanager.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class PageController {
    @GetMapping("/send_file.html")
    public String sendFilePage() {
        return "send_file";
    }

    @GetMapping("/next_file.html")
    public String nextFilePage() {
        return "next_file";
    }
}
