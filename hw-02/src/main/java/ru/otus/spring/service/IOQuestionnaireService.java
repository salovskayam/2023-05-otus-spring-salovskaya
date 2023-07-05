package ru.otus.spring.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.Questionnaire;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.StudentTestResult;
import ru.otus.spring.service.io.IOService;
import ru.otus.spring.service.utils.QuestionnaireUtil;

import java.util.ArrayList;
import java.util.List;

public class IOQuestionnaireService implements QuestionnaireService {

    private static final Logger LOGGER = LoggerFactory.getLogger(IOQuestionnaireService.class);

    private final IOService ioService;

    private final QuestionnaireFormatter printFormatter;

    public IOQuestionnaireService(IOService ioService, QuestionnaireFormatter printFormatter) {
        this.ioService = ioService;
        this.printFormatter = printFormatter;
    }

    @Override
    public StudentTestResult initTesting() {
        List<Integer> rightAnswers = List.of(1, 1, 1, 1, 1);
        String name = ioService.readStringWithPrompt("What's your name?");
        String surname = ioService.readStringWithPrompt("What's your surname?");
        Student student = new Student(name, surname);
        StudentTestResult studentTestResult = new StudentTestResult(student);
        studentTestResult.setRightAnswers(rightAnswers);
        return studentTestResult;
    }

    @Override
    public List<Integer> getStudentAnswers(List<Questionnaire> questionnaires) {
        List<Integer> studentAnswers = new ArrayList<>();
        questionnaires.forEach(q -> {
            List<String> options = q.answers();
            // не выводим вопросы без ответов
            if (CollectionUtils.isEmpty(options)) {
                return;
            }
            try {
                int input = handleStudentAnswer(q);
                studentAnswers.add(input);
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
                studentAnswers.add(0);
            }
        });
        return studentAnswers;
    }

    @Override
    public void finishTesting(StudentTestResult testResult) {
        boolean result = getStudentResult(testResult);
        testResult.setSuccess(result);
        ioService.outputString(testResult.toString());
    }

    private int handleStudentAnswer(Questionnaire q) {
        try {
            String output = printFormatter.format(q);
            int input = ioService.readIntWithPrompt(output);
            QuestionnaireUtil.checkAnswer(q.answers().size(), input);
            return input;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Value is invalid");
        }
    }

    private boolean getStudentResult(StudentTestResult testResult) {
        List<Integer> studentAnswers = testResult.getAnswers();
        List<Integer> rightAnswers = testResult.getRightAnswers();
        return studentAnswers.equals(rightAnswers);
    }
}
