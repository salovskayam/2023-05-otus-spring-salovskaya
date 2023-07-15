package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.service.io.IOService;
import ru.otus.spring.service.io.IOServiceStreams;

@PropertySource("classpath:application.properties")
@Configuration
public class AppConfig {
    @Bean
    IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }
}
