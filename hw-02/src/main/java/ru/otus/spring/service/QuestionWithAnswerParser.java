package ru.otus.spring.service;

import ru.otus.spring.domain.Questionnaire;

import java.util.Collections;
import java.util.List;

public class QuestionWithAnswerParser implements QuestionParser {

    private static final String DELIMITER = ", ";

    public Questionnaire parse(String text) {
        String[] questionWithAnswer = text.split(DELIMITER);
        return getQuestionnaire(List.of(questionWithAnswer));
    }

    private Questionnaire getQuestionnaire(List<String> questionWithAnswer) {
        String question = questionWithAnswer.get(0);
        List<String> answers = questionWithAnswer.size() > 1 ?
                questionWithAnswer.subList(1, questionWithAnswer.size()) : Collections.emptyList();
        return new Questionnaire(question, answers);
    }
}
