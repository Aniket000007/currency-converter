package com.example.currency.converter.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RateCacheServiceV2 {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 🔥 L1 Cache (in-memory)
    private final Map<String, Map<String, Double>> localCache = new ConcurrentHashMap<>();
    private final Map<String, Long> expiry = new ConcurrentHashMap<>();

    private static final long LOCAL_TTL_MS = 60_000 * 60 * 24; // 1 day

    private String key(String base) {
        return "rates:" + base.toUpperCase();
    }

    public Map<String, Double> getRates(String base) {
        base = base.toUpperCase();
        String key = key(base);

        long now = System.currentTimeMillis();

        // ✅ 1. Check in-memory cache first
        if (localCache.containsKey(key) && expiry.getOrDefault(key, 0L) > now) {
            return localCache.get(key);
        }

        // ✅ 2. Fallback to Redis
        try {
            Object value = redisTemplate.opsForValue().get(key);
            if (value instanceof Map) {
                Map<String, Double> rates = (Map<String, Double>) value;

                // 🔥 populate local cache
                localCache.put(key, rates);
                expiry.put(key, now + LOCAL_TTL_MS);

                return rates;
            }
        } catch (Exception e) {
            System.err.println("Redis read failed: " + e.getMessage());
        }

        return null;
    }

    public void putRates(String base, Map<String, Double> rates) {
        base = base.toUpperCase();
        String key = key(base);

        long now = System.currentTimeMillis();

        // ✅ 1. Update local cache
        localCache.put(key, rates);
        expiry.put(key, now + LOCAL_TTL_MS);

        // ✅ 2. Update Redis
        try {
            System.out.println("Caching rates for base: " + base);
            redisTemplate.opsForValue().set(key, rates, Duration.ofHours(1));
        } catch (Exception e) {
            System.err.println("Failed to cache rates: " + e.getMessage());
        }
    }
}