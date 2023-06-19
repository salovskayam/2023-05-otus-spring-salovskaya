package ru.otus.spring.service;

import ru.otus.spring.domain.Questionnaire;

import java.util.Collections;
import java.util.List;

public class QuestionWithAnswerParser implements QuestionParser {

    private static final String DELIMITER = ", ";

    public Questionnaire parse(String question) {
        return getQuestionnaire(List.of(question.split(DELIMITER)));
    }

    private Questionnaire getQuestionnaire(List<String> question) {
        return new Questionnaire(question.get(0), question.size() > 1 ?
                question.subList(1, question.size()) : Collections.emptyList());
    }
}
