package com.api.chatop.chatop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

@Configuration
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();

        Resource[] resources = new Resource[] {
            new ClassPathResource(".env"),
            new FileSystemResource(".env"),
            new FileSystemResource("chatop/.env")
        };

        configurer.setLocations(resources);
        configurer.setIgnoreResourceNotFound(true);

        return configurer;
    }
}
