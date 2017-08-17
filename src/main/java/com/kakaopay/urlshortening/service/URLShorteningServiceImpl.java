package com.kakaopay.urlshortening.service;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.kakaopay.urlshortening.repository.URLRepository;
import com.kakaopay.urlshortening.utils.Base62Codec;

@Service
public class URLShorteningServiceImpl implements URLShorteningService {

    private static final String SHORT_URL_PREFIX = "http://kakao.pay/";
    
    private final URLRepository urlRepository;
    private final Base62Codec base62Codec;
    private Logger logger;
    
    public URLShorteningServiceImpl(URLRepository urlRepository, Base62Codec base62Codec, Logger logger) {
        this.urlRepository = urlRepository;
        this.base62Codec = base62Codec;
        this.logger = logger; 
    }

    @Override
    public String shortenURL(String url) {
        if (urlRepository.isFull() || url == null) {
            return url;
        }
        
        long index;
        String shortURL;
        
        do {
            index = indexGenerator();
            shortURL = SHORT_URL_PREFIX + base62Codec.encode(index);
        } while(urlRepository.hasShortenedURL(shortURL) && !urlRepository.isFull());
        
        urlRepository.putURL(shortURL, url);
        
        return shortURL;
    }

    @Override
    public String restoreURL(String shortURL) {
        if(urlRepository.isEmpty() || shortURL == null) {
            return shortURL;
        }
        
        String url = urlRepository.getURL(shortURL);
        
        return url.equals("") ? shortURL : url;
    }

    @Override
    public boolean isShortenedURL(String url) {
        return url.contains(SHORT_URL_PREFIX);
    }
    
    private long indexGenerator() {
        long index = 0L;
        
        // Generates index number between Base64 code "10000000" ~ "ZZZZZZZZ"
        // This can generate up to 214818490978688 index numbers.
        index = ThreadLocalRandom.current().nextLong(3521614606208L, 218340105584895L);
        logger.debug("Generated ID: {}", index);
        
        return index;
    }

}
