package com.kakaopay.urlshortening.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class URLShorteningServiceTest {
    
    @Autowired
    private URLShorteningService urlShorteningService;
    
    private String prefix;
    private String url;
    private String shortURL;

    @Before
    public void setUp() throws Exception {
        prefix = "http://kakao.pay/";
        url = "http://test-url.com/";
        shortURL = "http://kakao.pay/test_URL";
    }
    
    @Test
    public void shortenURL() throws Exception {
        // Given
        
        // When
        String result1 = urlShorteningService.shortenURL(url);
        String result2 = urlShorteningService.shortenURL(url);
        
        // Then
        assertThat(result1.startsWith(prefix), is(true));
        assertThat(result1.substring(prefix.length()).length(), is(8));
        assertThat(result2, is(result1));
    }
    
    @Test
    public void inputInvalidURLIntoShortenURL() throws Exception {
        // Given
        String emptyURL = "  ";
        String spacesAroundURL = " http://kakaocorp.com/";
        String noSchemeURL = "kakaocorp.com";
        
        // When
        String emptyResult = urlShorteningService.shortenURL(emptyURL);
        String spacesResult = urlShorteningService.shortenURL(spacesAroundURL);
        String noSchemeResult = urlShorteningService.shortenURL(noSchemeURL);
        
        // Then
        assertThat(emptyResult, is(emptyURL));
        
        assertThat(spacesResult.startsWith(prefix), is(true));
        assertThat(spacesResult.substring(prefix.length()).length(), is(8));
        assertThat(urlShorteningService.restoreURL(spacesResult), is(spacesAroundURL.trim()));
        
        assertThat(noSchemeResult.startsWith(prefix), is(true));
        assertThat(noSchemeResult.substring(prefix.length()).length(), is(8));
        assertThat(urlShorteningService.restoreURL(noSchemeResult), is("http://" + noSchemeURL));
        
    }
    
    @Test
    public void restoreURL() throws Exception {
        // Given
        String expectedShortURL = urlShorteningService.shortenURL(url);
        
        // When
        String result = urlShorteningService.restoreURL(expectedShortURL);
        
        // Then
        assertThat(result, is(url));
    }
    
    @Test
    public void checkIsShortenedURL() throws Exception {
        // Given
        
        // When
        boolean result1 = urlShorteningService.isShortenedURL(url);
        boolean result2 = urlShorteningService.isShortenedURL(shortURL);
        
        // Then
        assertThat(result1, is(false));
        assertThat(result2, is(true));
    }

}
