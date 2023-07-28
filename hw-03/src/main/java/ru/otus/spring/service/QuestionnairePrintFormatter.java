package ru.otus.spring.service;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.Questionnaire;

import java.util.List;

@Component
public class QuestionnairePrintFormatter implements QuestionnaireFormatter {
    @Override
    public String format(Questionnaire questionnaire) {
        List<String> answers = questionnaire.answers();
        StringBuilder s = new StringBuilder(String.format("%s", questionnaire.question()));

        if (CollectionUtils.isEmpty(answers)) {
            return s.toString();
        }

        int cnt = 1;
        for (String answer : answers) {
            s.append(String.format("%n%d - %s;", cnt, answer));
            cnt++;
        }

        return s.toString();
    }
}
