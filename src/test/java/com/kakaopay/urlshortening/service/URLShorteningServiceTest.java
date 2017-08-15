package com.kakaopay.urlshortening.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class URLShorteningServiceTest {
    
    @Autowired
    private URLShorteningService urlShorteningService;

    @Before
    public void setUp() throws Exception {
    }
    
    @Test
    public void shortenURL() throws Exception {
        // Given
        String url = "http://naver.com/";
        
        // When
        String result = urlShorteningService.shortenURL(url);
        
        // Then
        assertThat(result, is("http://kakao.pay/test_URL"));
    }
    
    @Test
    public void restoreURL() throws Exception {
        // Given
        String url = "http://kakao.pay/test_URL";
        
        // When
        String result = urlShorteningService.restoreURL(url);
        
        // Then
        assertThat(result, is("http://naver.com/"));
    }
    
    @Test
    public void checkIsShortenedURL() throws Exception {
        // Given
        String url = "http://kakao.pay/test_URL";
        
        // When & Then
        assertThat(urlShorteningService.isShortenedURL(url), is(true));
    }

}
