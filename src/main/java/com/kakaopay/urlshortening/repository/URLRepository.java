package com.kakaopay.urlshortening.repository;

public interface URLRepository {
    String getURL(String shortURL);
    boolean putURL(String url, String shortURL);
    boolean removeURL(String shortURL);
    void init();
    boolean isEmpty();
    int size();
}
