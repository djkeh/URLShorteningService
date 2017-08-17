package com.kakaopay.urlshortening.utils;

import static org.hamcrest.CoreMatchers.*;
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
public class Base62CodecTest {

    @Autowired
    private Base62Codec base62Codec;
    
    private String testBase62Code;
    private long testNumber;
    
    @Before
    public void setUp() {
        testBase62Code = "abc";
        testNumber = 39134L;
    }

    @Test
    public void testCharacters() throws Exception {
        // Given
        String str = "";
        for (int i = 0; i <= 9; ++i) {
            str += i;
        }
        for (char c = 'a'; c <= 'z'; ++c) {
            str += c;
        }
        for (char c = 'A'; c <= 'Z'; ++c) {
            str += c;
        }
        
        // When & Then
        assertThat(base62Codec.getCHARACTERS(), is(str));
    }

    @Test
    public void testNumberToString() throws Exception {
        // Given
        int n = 0;
        char[] chars = testBase62Code.toCharArray();
        
        for (int i = 0; i < testBase62Code.length(); ++i) {
            n += base62Codec.getCHARACTERS().indexOf(chars[i]) * (int) Math.pow(62, testBase62Code.length() -1 - i);
        }

        // When & Then
        assertThat(base62Codec.encode(n), is(testBase62Code));
    }

    @Test
    public void testStringToNumber() throws Exception {
        // Given
        
        // When & Then
        assertThat(base62Codec.decode(testBase62Code), is(testNumber));
    }

    @Test
    public void testZeroOneValue() throws Exception {
        // Given
        
        // When & Then
        assertThat(base62Codec.encode(0), is("0"));
        assertThat(base62Codec.encode(1), is("1"));
    }
    
    @Test
    public void testBase62Length() throws Exception {
        // Given
        
        // When & Then
        assertThat(base62Codec.getLENGTH(), is(62));
    }

}
