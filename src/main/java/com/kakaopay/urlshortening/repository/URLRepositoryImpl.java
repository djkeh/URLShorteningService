package com.kakaopay.urlshortening.repository;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public class URLRepositoryImpl implements URLRepository {
    
    private final Map<String, String> map;
    
    public URLRepositoryImpl() {
        map = new HashMap<>();
    }
    
    @Override
    public String getURL(String shortURL) {
        return map.getOrDefault(shortURL, "");
    }

    @Override
    public boolean putURL(String url, String shortURL) {
        return map.putIfAbsent(shortURL, url) == null ? true : false;
    }

    @Override
    public boolean removeURL(String shortURL) {
        return map.remove(shortURL) == null ? false : true;
    }

    @Override
    public void init() {
        map.clear();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public int size() {
        return map.size();
    }

}
