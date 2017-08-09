package com.kakaopay.urlshortening.configuration;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.kakaopay.urlshortening.configuration.Thymeleaf3Config.Thymeleaf3Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Thymeleaf3ConfigTest {
    
    @Autowired
    private Thymeleaf3Config thymeleaf3Config;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSetApplicationContext() throws Exception {
        // Given
        
        // When
        ApplicationContext applicationContext = (ApplicationContext) ReflectionTestUtils.getField(thymeleaf3Config, "applicationContext");
        
        // Then
        assertThat(applicationContext, is(not(nullValue())));
    }

    @Test
    public void testThymeleafViewResolver() throws Exception {
        // Given
        
        // When
        ThymeleafViewResolver viewResolver = (ThymeleafViewResolver) thymeleaf3Config.thymeleafViewResolver();
        
        // Then
        assertThat(viewResolver, is(not(nullValue())));
        assertThat(viewResolver.getCharacterEncoding(), is("UTF-8"));
        assertThat(viewResolver.getTemplateEngine(), is(not(nullValue())));
    }

    @Test
    public void testThymeleafTemplateEngine() throws Exception {
        // Given
        
        // When
        SpringTemplateEngine engine = (SpringTemplateEngine) thymeleaf3Config.thymeleafTemplateEngine();
        
        // Then
        assertThat(engine, is(not(nullValue())));
        assertThat(engine.getEnableSpringELCompiler(), is(true));
        assertThat(engine.getTemplateResolvers(), is(not(nullValue())));
    }

    @Test
    public void testThymeleafTemplateResolver() throws Exception {
        // Given
        Thymeleaf3Properties properties = (Thymeleaf3Properties) ReflectionTestUtils.getField(thymeleaf3Config, "properties");
        Integer order = properties.getTemplateResolverOrder();
        
        // When
        SpringResourceTemplateResolver resolver = (SpringResourceTemplateResolver) thymeleaf3Config.thymeleafTemplateResolver();
        
        // Then
        assertThat(resolver, is(not(nullValue())));
        assertThat(resolver.getPrefix(), is(properties.getPrefix()));
        assertThat(resolver.getSuffix(), is(properties.getSuffix()));
        assertThat(resolver.getTemplateMode().name(), is(properties.getMode()));
        assertThat(resolver.getUseDecoupledLogic(), is(properties.isDecoupledLogic()));
        if(properties.getEncoding() != null) {
            assertThat(resolver.getCharacterEncoding(), is(properties.getEncoding().name()));
        }
        assertThat(resolver.isCacheable(), is(properties.isCache()));
        if(order != null) {
            assertThat(resolver.getOrder(), is(order));
        }
        
    }

}
