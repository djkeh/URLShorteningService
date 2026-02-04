package com.kakaopay.urlshortening.service;

import com.kakaopay.urlshortening.repository.URLRepository;
import com.kakaopay.urlshortening.utils.Base62Codec;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class URLShorteningServiceImpl implements URLShorteningService {

    private static final String SHORT_URL_PREFIX = "http://kakao.pay/";
    private static final String HTTP_SCHEME = "http://";
    private static final String HTTPS_SCHEME = "https://";

    private final IndexGeneratorService indexGeneratorService;
    private final URLRepository urlRepository;
    private final Base62Codec base62Codec;
    private final Logger logger;
    
    public URLShorteningServiceImpl(IndexGeneratorService feistelNetworkGenerator, URLRepository urlRepository, Base62Codec base62Codec, Logger logger) {
        this.indexGeneratorService = feistelNetworkGenerator;
        this.urlRepository = urlRepository;
        this.base62Codec = base62Codec;
        this.logger = logger; 
    }

    @Cacheable("url")
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
            index = indexGeneratorService.nextValue();
            shortURL = SHORT_URL_PREFIX + base62Codec.encode(index);

            logger.debug("[Generated ID] {}", index);
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

}
