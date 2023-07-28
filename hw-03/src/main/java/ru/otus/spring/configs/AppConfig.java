package ru.otus.spring.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.io.IOService;
import ru.otus.spring.service.io.IOServiceStreams;

@Configuration
@EnableConfigurationProperties(AppProps.class)
public class AppConfig {
    @Bean
    IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }
}
