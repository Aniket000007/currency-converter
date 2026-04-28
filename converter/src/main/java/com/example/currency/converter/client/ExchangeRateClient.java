package com.example.currency.converter.client;

import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.currency.converter.dto.ExchangeApiResponse;

@Service
public class ExchangeRateClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String BASE_URL =
            "https://v6.exchangerate-api.com/v6/b948a106a745afda5a109acc/latest/";

    public Map<String, Double> getRates(String base) {

        String url = BASE_URL + base;

        ExchangeApiResponse response =
            restTemplate.getForObject(url, ExchangeApiResponse.class);

        return response.getConversion_rates();
    }
}