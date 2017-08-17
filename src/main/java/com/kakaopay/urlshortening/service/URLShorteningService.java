package com.kakaopay.urlshortening.service;

public interface URLShorteningService {
    
    /**
     * Creates shortened URL and store it to the repository.
     * 
     * @param url Original URL as an input
     * @return Shortened URL, or the input URL if the job fails for some reason
     */
    String shortenURL(String url);
    
    /**
     * Returns restored URL from the shortened URL
     * 
     * @param shortURL Shortened URL as an input
     * @return Original URL, or the input shortURL if the job fails for some reasion
     */
    String restoreURL(String shortURL);
    
    /**
     * Checks if the input URL is shortened URL in a simple way.
     * 
     * @param url URL which you want to check
     * @return true if it contains certain prefix for validation.
     */
    boolean isShortenedURL(String url);
}
