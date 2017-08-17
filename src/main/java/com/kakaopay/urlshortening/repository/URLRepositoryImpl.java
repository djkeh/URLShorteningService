package com.kakaopay.urlshortening.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
        return map.size() == Integer.MAX_VALUE ? true : false;
    }

    @Override
    public int size() {
        return map.size();
    }

}
