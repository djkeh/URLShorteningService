package com.kakaopay.urlshortening.service;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.urlshortening.repository.URLRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@AutoConfigureMockMvc
public class URLShorteningServiceTest {
    
    @Autowired
    private URLShorteningService urlShorteningService;
    
    @MockBean
    private URLRepository urlRepository;
    
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
        when(urlRepository.putURL(anyString(), eq(url))).thenReturn(true);
        
        // When
        String result = urlShorteningService.shortenURL(url);
        
        // Then
        assertThat(result, containsString(prefix));
        assertThat(result.substring(prefix.length()).length(), is(8));
    }
    
    @Test
    public void restoreURL() throws Exception {
        // Given
        when(urlRepository.getURL(shortURL)).thenReturn(url);
        
        // When
        String result = urlShorteningService.restoreURL(shortURL);
        
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
