package com.kakaopay.urlshortening.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    private String navbarBrand;
    private String paramURL;
    private String testURL;
    private String testShortenedURL;
    
    @Before
    public void setUp() throws Exception {
        navbarBrand = "URL Shortening Sevice : Demo";
        paramURL = "url";
        testURL = "http://test-url.com/";
        testShortenedURL = "http://kakao.pay/test_URL";
    }
    
    /**
     * Getting into the main page returns one constant page with the specific message: {@code navbarBrand}
     * 
     * @throws Exception
     */
    @Test
    public void testMain() throws Exception {
        // Given
        
        // When & Then
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(navbarBrand)));
    }
    
    /**
     * Clicking submit button(POST request) with regular URL returns shortening result.
     * 
     * @throws Exception
     */
    @Test
    public void ShortenURLRequest() throws Exception {
        // Given
        
        // When & Then
        mockMvc.perform(post("/").param(paramURL, testURL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(navbarBrand)))
                .andExpect(content().string(containsString(testShortenedURL)));
    }
    
    /**
     * Clicking submit button(POST request) with shortened URL performs 301 redirection.
     * 
     * @throws Exception
     */
    @Test
    public void redirectShortenedURLRequest() throws Exception {
        // Given
        
        // When & Then
        mockMvc.perform(post("/").param(paramURL, testShortenedURL))
                .andDo(print())
                .andExpect(status().isFound());
    }

}
