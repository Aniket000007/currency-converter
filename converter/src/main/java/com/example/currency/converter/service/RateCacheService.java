package com.example.currency.converter.service;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateCacheService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private String key(String base) {
        return "rates:" + base;
    }

    public Map<String, Double> getRates(String base) {
        return (Map<String, Double>) redisTemplate.opsForValue().get(key(base));
    }

    public void putRates(String base, Map<String, Double> rates) {
        System.out.println("Caching rates for base: " + base);
        try {
            redisTemplate.opsForValue().set(key(base), rates, Duration.ofHours(24));
        } catch (Exception e) {
            System.err.println("Failed to cache rates: " + e.getMessage());
        }
    }
}
