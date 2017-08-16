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
     * Checks whether the URL repository is empty.
     * 
     * @return true if the repository is empty
     */
    boolean isEmpty();
    
    /**
     * Returns the number of stored URL entities in the URL repository.
     * 
     * @return number of URL entities
     */
    int size();
}
