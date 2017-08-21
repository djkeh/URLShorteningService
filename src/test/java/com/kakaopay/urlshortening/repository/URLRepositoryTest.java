package com.kakaopay.urlshortening.repository;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class URLRepositoryTest {
    
    @Autowired
    private URLRepository beanURLRepository;
    
    private URLRepository testURLRepository;
    
    private String testURL;
    private String testShortURL;
    private int testMaxSize;

    @Before
    public void setUp() throws Exception {
        testMaxSize = 5;
        testURLRepository = new URLRepositoryImpl(new HashMap<>(), testMaxSize);
        testURL = "http://test-url.com/";
        testShortURL = "http://kakao.pay/test_URL";
    }

    @Test
    public void testGetUrl() throws Exception {
        // Given
        testURLRepository.init();
        testURLRepository.putURL(testShortURL, testURL);
        
        // When
        String actualURL = testURLRepository.getURL(testShortURL);
        
        // Then
        assertThat(actualURL, is(testURL));
    }
    
    @Test
    public void testGetShortURL() throws Exception {
        // Given
        testURLRepository.init();
        testURLRepository.putURL(testShortURL, testURL);
        
        // When
        String acturlShortURL = testURLRepository.getShortURL(testURL);
        
        // Then
        assertThat(acturlShortURL, is(testShortURL));
    }
    
    @Test
    public void testPutUrl() throws Exception {
        // Given
        testURLRepository.init();
        int count = testURLRepository.size();
        
        // When
        boolean putResult = testURLRepository.putURL(testShortURL, testURL);
        
        // Then
        assertThat(putResult, is(true));
        assertThat(testURLRepository.size(), is(count + 1));
    }

    @Test
    public void testRemoveURL() throws Exception {
        // Given
        testURLRepository.putURL(testShortURL, testURL);
        int size = testURLRepository.size();
        
        // When
        boolean removeResult = testURLRepository.removeURL(testShortURL);
        
        // Then
        assertThat(removeResult, is(true));
        assertThat(testURLRepository.getURL(testShortURL), is(""));
        assertThat(testURLRepository.size(), is(size - 1));
    }
    
    @Test
    public void testInit() throws Exception {
        // Given
        
        // When
        testURLRepository.init();
        
        // Then
        assertThat(testURLRepository.size(), is(0));
        
    }

    @Test
    public void testHasShortenedURL() throws Exception {
        // Given
        testURLRepository.putURL(testShortURL, testURL);
        
        // When
        boolean result1 = testURLRepository.hasShortenedURL(testShortURL);
        boolean result2 = testURLRepository.hasShortenedURL("false test");
        
        // Then
        assertThat(result1, is(true));
        assertThat(result2, is(false));
        
    }
    
    @Test
    public void testHasURL() throws Exception {
        // Given
        testURLRepository.putURL(testShortURL, testURL);
        
        // When
        boolean result = testURLRepository.hasURL(testURL);
        
        // Then
        assertThat(result, is(true));
    }
    
    @Test
    public void testIsEmpty() throws Exception {
        // Given
        testURLRepository.init();
        
        // When
        boolean empty = testURLRepository.isEmpty();
        
        // Then
        assertThat(empty & testURLRepository.size() == 0, is(true));
    }

    @Test
    public void testIsFull() throws Exception {
        // Given
        for (int i = 0; i < testURLRepository.getMaxSize(); ++i) {
            testURLRepository.putURL("dummy" + i, "dummy");
        }
        
        // When
        boolean result = testURLRepository.isFull();
        
        // Then
        assertThat(result, is(true));
    }
    
    @Test
    public void testSize() throws Exception {
        // Given
        testURLRepository.init();
        
        // When
        int size = testURLRepository.size();
        
        // Then
        assertThat(size, is(0));
    }
    
    @Test
    public void testGetMaxSize() throws Exception {
        // Given
        
        // When
        int maxBeanSize = beanURLRepository.getMaxSize();
        int maxTestSize = testURLRepository.getMaxSize();
        
        // Then
        assertThat(maxBeanSize, is(Integer.MAX_VALUE));
        assertThat(maxTestSize, is(testMaxSize));
    }
}
