package ru.otus.spring.configs;

import org.springframework.boot.context.properties.ConfigurationProperties;

import org.springframework.boot.context.properties.bind.ConstructorBinding;

import java.util.Locale;

@ConfigurationProperties(prefix = "application")
public class AppProps {

    private final Locale locale;

    @ConstructorBinding
    public AppProps(Locale locale) {
        this.locale = locale;
    }

    public Locale getLocale() {
        return locale;
    }
}
