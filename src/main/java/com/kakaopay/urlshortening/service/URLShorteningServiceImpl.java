package com.kakaopay.urlshortening.service;

import org.springframework.stereotype.Service;

@Service
public class URLShorteningServiceImpl implements URLShorteningService {

    @Override
    public String shortenURL(String url) {
        if(url.equals("http://test-url.com/")) return "http://kakao.pay/test_URL";
        return url;
    }

    @Override
    public String restoreURL(String url) {
        if(url == null) return null;
        if(url.equals("http://kakao.pay/test_URL")) return "http://test-url.com/";
        else return url;
    }

    @Override
    public boolean isShortenedURL(String url) {
        if(url.equals("http://kakao.pay/test_URL")) return true;
        return false;
    }

}
