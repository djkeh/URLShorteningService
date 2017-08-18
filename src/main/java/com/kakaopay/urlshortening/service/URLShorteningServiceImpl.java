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
    private static final String HTTP_SCHEME = "http://";
    private static final String HTTPS_SCHEME = "https://";
    
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
            return "";
        }
        
        url = url.trim();
        if (!url.startsWith(HTTP_SCHEME) && !url.startsWith(HTTPS_SCHEME)) {
            url = HTTP_SCHEME + url;
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
            return "";
        }
        
        shortURL = shortURL.trim();
        String url = urlRepository.getURL(shortURL);
        logger.debug("[Restored URL ({})] {}", shortURL, url);
        
        return url;
    }

    @Override
    public boolean isShortenedURL(String url) {
        return url.contains(SHORT_URL_PREFIX);
    }
    
    /**
     * Generates index number between Base62 code "10000000" ~ "ZZZZZZZZ"<br>
     * This can generate up to 214818490978688 index numbers.
     * 
     * @return generated index number in certain range
     */
    private long indexGenerator() {
        long index = 0L;
        
        index = ThreadLocalRandom.current().nextLong(3521614606208L, 218340105584895L);
        logger.debug("[Generated ID] {}", index);
        
        return index;
    }

}
