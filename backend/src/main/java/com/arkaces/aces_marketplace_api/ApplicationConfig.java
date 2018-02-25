package com.arkaces.aces_marketplace_api;

import com.arkaces.aces_marketplace_api.common.IdentifierGenerator;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

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
            .build();
    }
}
