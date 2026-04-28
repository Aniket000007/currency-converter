package com.example.currency.converter.dto;

import java.util.Map;

import lombok.Data;

@Data
public class ExchangeApiResponse {

    private String result;
    private String base_code;

    private Map<String, Double> conversion_rates;
}
