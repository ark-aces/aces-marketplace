package com.arkaces.aces_marketplace_api;

import com.arkaces.aces_marketplace_api.common.ResourceService;
import org.apache.commons.validator.routines.EmailValidator;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Configuration
public class ApplicationConfig {
    
    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.indentOutput(true);
        return builder;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addConverter(new AbstractConverter<BigDecimal, String>() {
            protected String convert(BigDecimal source) {
                return source == null ? null : source.toPlainString();
            }
        });

        modelMapper.addConverter(new AbstractConverter<LocalDateTime, String>() {
            protected String convert(LocalDateTime source) {
                return source == null ? null : source.atOffset(ZoneOffset.UTC).toString();
            }
        });

        return modelMapper;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(10, new SecureRandom());
    }
    
    @Bean
    public RestTemplate serviceRestTemplate() {
        // todo: restrict to json
        // todo: add a marketplace instance header
        return new RestTemplateBuilder()
            .setConnectTimeout(2000)
            .setReadTimeout(60000) // todo: reduce timeout
            .build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
    
    @Bean
    public EmailValidator emailValidator() {
        return EmailValidator.getInstance();
    }
    
    @Bean
    public String verificationEmailTemplate(ResourceService resourceService) {
        return resourceService.read("email-templates/verification-email.text.mustache");
    }

    @Bean
    public String resetPasswordEmailTemplate(ResourceService resourceService) {
        return resourceService.read("email-templates/reset-password-email.text.mustache");
    }

    @Bean
    public String baseUrl(Environment environment) {
        return environment.getProperty("baseUrl");
    }
}
