package com.example.currency.converter.config;

import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class NativeLibConfig {

    @PostConstruct
    public void init() {
        System.setProperty("jna.library.path", "/opt/homebrew/lib");
        System.setProperty("java.library.path", "/opt/homebrew/lib");
        System.setProperty("TESSDATA_PREFIX", "/opt/homebrew/opt/tesseract/share/tessdata");
    }
}