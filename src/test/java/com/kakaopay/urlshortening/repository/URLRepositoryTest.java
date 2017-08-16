package com.kakaopay.urlshortening.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class URLRepositoryTest {
    
    @Autowired
    private URLRepository urlRepository;
    
    private String testURL;
    private String testShortURL;

    @Before
    public void setUp() throws Exception {
        testURL = "http://test-url.com/";
        testShortURL = "http://kakao.pay/test_URL";
    }

    @Test
    public void testGetUrl() throws Exception {
        // Given
        boolean result = urlRepository.putURL(testURL, testShortURL);
        
        // When
        String actualURL = urlRepository.getURL(testShortURL);
        
        // Then
        assertThat(result, is(true));
        assertThat(actualURL, is(testURL));
    }
    
    @Test
    public void testPutUrl() throws Exception {
        // Given
        int count = urlRepository.size();
        
        // When
        boolean result = urlRepository.putURL(testURL, testShortURL);
        
        // Then
        assertThat(urlRepository.size(), is(count + 1));
        assertThat(result, is(true));
    }

    @Test
    public void testRemoveURL() throws Exception {
        // Given
        int size = urlRepository.size();
        
        // When
        boolean result = urlRepository.removeURL(testShortURL);
        
        // Then
        assertThat(result, is(true));
        assertThat(urlRepository.getURL(testShortURL), is(""));
        assertThat(urlRepository.size(), is(size - 1));
    }
    
    @Test
    public void testInit() throws Exception {
        // Given
        
        // When
        boolean result = urlRepository.init();
        
        // Then
        assertThat(result, is(true));
        assertThat(urlRepository.size(), is(0));
        
    }
    
    @Test
    public void testIsEmpty() throws Exception {
        // Given
        urlRepository.init();
        
        // When
        boolean empty = urlRepository.isEmpty();
        
        // Then
        assertThat(empty & urlRepository.size() == 0, is(true));
    }

    @Test
    public void testSize() throws Exception {
        // Given
        urlRepository.init();
        
        // When
        int size = urlRepository.size();
        
        // Then
        assertThat(size, is(0));
    }
}
