package ru.otus.spring.service;

import ru.otus.spring.dao.QuestionnaireProvider;
import ru.otus.spring.domain.Questionnaire;
import ru.otus.spring.domain.StudentTestResult;

import java.util.List;

public class QuestionnaireRunner {

    private final QuestionnaireProvider questionnaireProvider;

    private final QuestionnaireService questionnaireService;

    public QuestionnaireRunner(QuestionnaireProvider questionnaireProvider,
                               QuestionnaireService questionnaireService) {
        this.questionnaireProvider = questionnaireProvider;
        this.questionnaireService = questionnaireService;
    }

    public void execute() {
        StudentTestResult studentTestResult = questionnaireService.initTesting();

        List<Questionnaire> questionnaires = questionnaireProvider.get();
        List<Integer> studentAnswers = questionnaireService.getStudentAnswers(questionnaires);
        studentTestResult.setAnswers(studentAnswers);

        questionnaireService.finishTesting(studentTestResult);
    }

}
