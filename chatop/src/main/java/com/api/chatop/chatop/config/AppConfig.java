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
            new ClassPathResource(".env"),           // Dans src/main/resources
            new FileSystemResource(".env"),          // À la racine du projet
            new FileSystemResource("chatop/.env")    // Si lancé depuis le parent
        };

        configurer.setLocations(resources);
        configurer.setIgnoreResourceNotFound(true);  // Ne pas échouer si un fichier n'existe pas

        return configurer;
    }
}
