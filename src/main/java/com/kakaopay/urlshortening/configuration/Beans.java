package com.kakaopay.urlshortening.configuration;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.kakaopay.urlshortening.utils.Base62Codec;


@Configuration
public class Beans {
    
    @Bean
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public Logger logger(InjectionPoint injectionPoint) {
        return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
    }
    
    @Bean
    public Base62Codec base62Codec() {
        return new Base62Codec();
    }
    
    @Bean
    public Map<String, String> map() {
        return new HashMap<>();
    }
}
