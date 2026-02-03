package com.kakaopay.urlshortening.service;

import com.kakaopay.urlshortening.repository.URLRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.times;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class URLShorteningServiceTest {
    
    @Autowired
    private URLShorteningService urlShorteningService;

    @MockitoSpyBean
    private URLRepository urlRepository;

    @Autowired
    private CacheManager cacheManager;
    
    private String prefix;
    private String url;
    private String shortURL;

    @BeforeEach
    public void setUp() throws Exception {
        prefix = "http://kakao.pay/";
        url = "http://test-url.com/";
        shortURL = "http://kakao.pay/test_URL";
    }

    @AfterEach
    public void tearDown() throws Exception {
        urlRepository.init();
        cacheManager.getCache("url").clear();
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
        then(urlRepository).should(times(1)).putURL(any(), any());
    }
    
    @Test
    public void inputInvalidURLIntoShortenURL() throws Exception {
        // Given
        String emptyURL = "  ";
        String spacesAroundURL = " http://kakaocorp.com/";
        String noSchemeURL = "kakaocorp.com";
        String httpsURL = "https://kakaocorp.com/";
        
        // When
        String emptyResult = urlShorteningService.shortenURL(emptyURL);
        String spacesResult = urlShorteningService.shortenURL(spacesAroundURL);
        String noSchemeResult = urlShorteningService.shortenURL(noSchemeURL);
        String httpsResult = urlShorteningService.shortenURL(httpsURL);
        
        // Then
        assertThat(emptyResult, is(""));
        
        assertThat(spacesResult.startsWith(prefix), is(true));
        assertThat(spacesResult.substring(prefix.length()).length(), is(8));
        assertThat(urlShorteningService.restoreURL(spacesResult), is(spacesAroundURL.trim()));
        
        assertThat(noSchemeResult.startsWith(prefix), is(true));
        assertThat(noSchemeResult.substring(prefix.length()).length(), is(8));
        assertThat(urlShorteningService.restoreURL(noSchemeResult), is("http://" + noSchemeURL));
        
        assertThat(httpsResult.startsWith(prefix), is(true));
        assertThat(httpsResult.substring(prefix.length()).length(), is(8));
        assertThat(urlShorteningService.restoreURL(httpsResult), is(httpsURL));
        
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
    public void inputInvalidShortURLIntoRestoreURL() throws Exception {
        // Given
        String expectedShortURL = urlShorteningService.shortenURL(url);
        
        // When
        String untrimmedResult = urlShorteningService.restoreURL(" " + expectedShortURL);
        String badResult = urlShorteningService.restoreURL("bad shortURL");
        
        // Then
        assertThat(untrimmedResult, is(url));
        assertThat(badResult, is(""));
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
