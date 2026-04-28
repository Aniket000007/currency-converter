package com.example.currency.converter.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.currency.converter.client.ExchangeRateClient;

@Service
public class ConversionService {

    @Autowired
    private ExchangeRateClient client;

    @Autowired
    private RateCacheServiceV2 cache;

    public double convert(String from, String to, double amount) {

        Map<String, Double> rates = cache.getRates(from);
        if (rates == null) {
            rates = client.getRates(from);
            cache.putRates(from, rates);
        }
        

        Double rate = rates.get(to);

        if (rate == null) {
            throw new RuntimeException("Currency not found: " + to);
        }

        return amount * rate;
    }
}
