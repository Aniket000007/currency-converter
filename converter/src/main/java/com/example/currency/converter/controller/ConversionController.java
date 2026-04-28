package com.example.currency.converter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.currency.converter.service.ConversionService;

@RestController
public class ConversionController {

    @Autowired
    private ConversionService conversionService;

    @GetMapping("/api/convert")
    public double convert(@RequestParam String from, @RequestParam String to, @RequestParam double amount) {
        return conversionService.convert(from, to, amount);
    }
}
