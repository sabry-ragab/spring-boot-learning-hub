package com.kfh.education.service;

public interface CacheService<K, V> {
	void put(K key, V value);

	V get(K key);

	void remove(K key);
}