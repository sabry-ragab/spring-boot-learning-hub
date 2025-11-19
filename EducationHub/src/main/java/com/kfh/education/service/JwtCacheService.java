package com.kfh.education.service;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

@Service
public class JwtCacheService implements CacheService<String, String> {
	private final Cache<String, String> jwtCache;

    public JwtCacheService() {
        this.jwtCache = Caffeine.newBuilder()
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .maximumSize(1000)
                .build();
    }

	@Override
	public void put(String key, String value) {
		this.jwtCache.put(key, value);
	}

	@Override
	public String get(String key) {
        return this.jwtCache.getIfPresent(key);
	}

	@Override
	public void remove(String key) {
		this.jwtCache.invalidate(key);
	}

}
