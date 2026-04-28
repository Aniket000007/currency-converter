package com.example.currency.converter.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.currency.converter.service.ConversionService;
import com.example.currency.converter.service.OcrService;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class ScanController {

    @Autowired
    private OcrService ocrService;

    @Autowired
    private ConversionService conversionService;

    @PostMapping("/scan")
    public Object scan(
            @RequestParam("file") MultipartFile file,
            @RequestParam String from,
            @RequestParam String to
    ) throws Exception {

        byte[] bytes = file.getBytes();

        Double amount = ocrService.extractAmount(bytes);

        if (amount == null) {
            return Map.of("error", "Could not detect number");
        }

        double converted = conversionService.convert(from, to, amount);

        System.out.println("Detected -> " + amount);
        System.out.println("From -> " + from);
        System.out.println("To -> " + to);
        System.out.println("Converted -> " + converted);

        return Map.of(
                "amount", amount,
                "from", from,
                "to", to,
                "convertedAmount", converted
        );
    }
}