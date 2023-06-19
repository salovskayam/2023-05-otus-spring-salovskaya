package ru.otus.spring.service;

import ru.otus.spring.domain.Questionnaire;

public interface QuestionParser {
    Questionnaire parse(String question);
}
