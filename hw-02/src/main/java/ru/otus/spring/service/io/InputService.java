package ru.otus.spring.service.io;

public interface InputService {
    int readIntWithPrompt(String prompt);

    String readStringWithPrompt(String prompt);
}
