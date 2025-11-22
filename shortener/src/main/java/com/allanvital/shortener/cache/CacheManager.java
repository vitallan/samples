package com.allanvital.shortener.cache;

import com.allanvital.shortener.domain.pojo.ShortenedUrlResponse;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * mimic a redis or some other simple key-value cache
 *
 * @author Allan Vital (https://allanvital.com)
 */
@Component
public class CacheManager {

    public Map<String, ShortenedUrlResponse> cache = new HashMap<>();

    //this would be done in redis, so there would be no need for a LOCK handling
    private final Object LOCK = new Object();

    public void put(ShortenedUrlResponse response) {
        synchronized (LOCK) {
            cache.put(response.getShortened(), response);
        }
    }

    public ShortenedUrlResponse get(String key) {
        synchronized (LOCK) {
            return cache.get(key);
        }
    }

    public Set<Map.Entry<String, ShortenedUrlResponse>> getEntries() {
        return cache.entrySet();
    }

}
