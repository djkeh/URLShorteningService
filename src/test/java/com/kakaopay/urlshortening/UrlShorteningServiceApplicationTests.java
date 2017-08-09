package com.kakaopay.urlshortening;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.urlshortening.controller.MainController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlShorteningServiceApplicationTests {
    
    @Autowired
    private MainController mainController;

	@Test
	public void contextLoads() {
        // Given
        
        // When & Then
        assertThat(mainController, is(not(nullValue())));
	}

}
