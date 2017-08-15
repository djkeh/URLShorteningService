package com.kakaopay.urlshortening.service;

public interface URLShorteningService {
    String shortenURL(String url);
    String restoreURL(String url);
    boolean isShortenedURL(String url);
}
