package com.kakaopay.urlshortening.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;


/**
 * <p>
 * Custom {@link org.springframework.boot.autoconfigure.EnableAutoConfiguration Auto-configuration} for Thymeleaf
 * 3.<br>
 * </p>
 *
 * @author Uno Kim
 */
@Configuration
public class Thymeleaf3Config {

    @Bean
    public SpringResourceTemplateResolver thymeleafTemplateResolver(
            SpringResourceTemplateResolver defaultTemplateResolver,
            Thymeleaf3Properties thymeleaf3Properties
    ) {
        defaultTemplateResolver.setUseDecoupledLogic(thymeleaf3Properties.decoupledLogic());

        return defaultTemplateResolver;
    }

    /**
     * <p>
     * Properties for Thymeleaf 3.
     * </p>
     *
     * @see org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties
     * @author Uno Kim
     */
    @ConfigurationProperties("spring.thymeleaf3")
    public record Thymeleaf3Properties(boolean decoupledLogic) {}

}
