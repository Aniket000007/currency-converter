package com.example.currency.converter.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;

@Service
public class OcrService {

    public Double extractAmount(byte[] imageBytes) throws Exception {

        BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));

        Tesseract tesseract = new Tesseract();
        // tesseract.setDatapath("/opt/homebrew/opt/tesseract/share/tessdata");
        tesseract.setDatapath(System.getenv("TESSERACT_PATH"));
        tesseract.setLanguage("eng");

        String result = tesseract.doOCR(image);

        return extractNumber(result);
    }

    private Double extractNumber(String text) {
        try {
            return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
        } catch (Exception e) {
            return null;
        }
    }
}
