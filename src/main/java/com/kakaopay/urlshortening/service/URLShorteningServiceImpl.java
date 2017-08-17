package com.kakaopay.urlshortening.service;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.kakaopay.urlshortening.repository.URLRepository;
import com.kakaopay.urlshortening.utils.Base62Codec;

@Service
public class URLShorteningServiceImpl implements URLShorteningService {

    private static final String SHORT_URL_PREFIX = "http://kakao.pay/";
    private static final String HTTP_DEFAULT_SCHEME = "http://";
    
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
        if (urlRepository.isFull() || StringUtils.isBlank(url)) {
            return url;
        }
        
        url = url.trim();
        if (!url.startsWith(HTTP_DEFAULT_SCHEME)) {
            url = HTTP_DEFAULT_SCHEME + url;
        }
        
        if (urlRepository.hasURL(url)) {
            return urlRepository.getShortURL(url);
        }
        
        long index;
        String shortURL;
        
        
        do {
            index = indexGenerator();
            shortURL = SHORT_URL_PREFIX + base62Codec.encode(index);
        } while(urlRepository.hasShortenedURL(shortURL) && !urlRepository.isFull());
        
        urlRepository.putURL(shortURL, url);
        logger.debug("[Shortened URL ({})] {}", url, shortURL);
        
        return shortURL;
    }

    @Override
    public String restoreURL(String shortURL) {
        if(urlRepository.isEmpty() || StringUtils.isBlank(shortURL)) {
            return shortURL;
        }
        
        String url = urlRepository.getURL(shortURL);
        logger.debug("[Restored URL ({})] {}", shortURL, url);
        
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
        logger.debug("[Generated ID] {}", index);
        
        return index;
    }

}
