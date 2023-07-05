package ru.otus.spring.service;

import ru.otus.spring.domain.Questionnaire;
import ru.otus.spring.domain.StudentTestResult;

import java.util.List;

public interface QuestionnaireService {
    StudentTestResult initTesting();

    List<Integer> getStudentAnswers(List<Questionnaire> questionnaires);

    void finishTesting(StudentTestResult testResult);
}
