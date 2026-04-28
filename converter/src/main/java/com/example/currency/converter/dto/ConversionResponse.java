package com.example.currency.converter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConversionResponse {
    private double amount;
    private String from;
    private String to;
    private double rate;
    private double convertedAmount;
}
