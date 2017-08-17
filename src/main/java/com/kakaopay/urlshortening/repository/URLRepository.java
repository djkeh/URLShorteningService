package com.kakaopay.urlshortening.repository;

public interface URLRepository {
    
    /**
     * Retrieves full URL with the shortened URL input. 
     * 
     * @param shortURL a shortened URL input
     * @return the matching full URL, or an empty String if there isn't such shortened URL stored in the repository.
     */
    String getURL(String shortURL);
    
    /**
     * Retrieves shortened URL with the full URL input. 
     * 
     * @param url a full URL input
     * @return the matching shortened URL, or an empty String if there isn't such full URL stored in the repository.
     */
    String getShortURL(String url);
    
    /**
     * Adds Shortened URL / URL in key / value pair to the URL repository.
     * 
     * @param shortURL a shortened URL as key
     * @param url a normal URL as value
     * @return true if the shortened URL didn't exist and it's put into the repository. 
     */
    boolean putURL(String shortURL, String url);
    
    /**
     * Removes ShortenedURL key and its value.
     * 
     * @param shortURL key to remove from the URL repository 
     * @return true if it actually removed the value.
     */
    boolean removeURL(String shortURL);
    
    /**
     * Clears the storage of URL repository.
     */
    void init();
    
    /**
     * Checks if the repository already has shortened URL.
     * 
     * @param shortURL shortened URL to look for
     * @return true if exists
     */
    boolean hasShortenedURL(String shortURL);
    
    /**
     * Checks if the repository already has the input URL.
     * 
     * @param url URL to look for
     * @return true if exists
     */
    boolean hasURL(String url);
    
    /**
     * Checks whether the URL repository is empty.
     * 
     * @return true if the repository is empty
     */
    boolean isEmpty();

    /**
     * Checks whether the URL repository is full.
     * 
     * @return true if the repository is full and doesn't allow more data input
     */
    boolean isFull();
    
    /**
     * Returns the number of stored URL entities in the URL repository.
     * 
     * @return number of URL entities
     */
    int size();
}
