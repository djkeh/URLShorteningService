package com.kakaopay.urlshortening.repository;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class URLRepositoryImpl implements URLRepository {
    
    private final Map<String, String> map;
    private final int maxSize;
    
    @Autowired
    public URLRepositoryImpl(Map<String, String> map) {
        this.map = map;
        this.maxSize = Integer.MAX_VALUE;
    }

    public URLRepositoryImpl(Map<String, String> map, int maxSize) {
        this.map = map;
        this.maxSize = maxSize;
    }
    
    @Override
    public String getURL(String shortURL) {
        return map.getOrDefault(shortURL, "");
    }

    @Override
    public String getShortURL(String url) {
        String shortURL = "";
        
        for (Entry<String, String> entry : map.entrySet()) {
            if (entry.getValue().equals(url)) {
                shortURL = entry.getKey();
                break;
             }
        }
        
        return shortURL;
    }

    @Override
    public boolean putURL(String shortURL, String url) {
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
    public boolean hasShortenedURL(String shortURL) {
        return map.containsKey(shortURL);
    }
    
    @Override
    public boolean hasURL(String url) {
        return map.containsValue(url);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
    
    @Override
    public boolean isFull() {
        return map.size() == maxSize ? true : false;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public int getMaxSize() {
        return maxSize;
    }

}
