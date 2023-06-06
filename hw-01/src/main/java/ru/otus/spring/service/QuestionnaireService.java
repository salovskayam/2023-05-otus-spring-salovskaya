package ru.otus.spring.service;

import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.Questionnaire;

import java.util.List;
import java.util.Scanner;

public class QuestionnaireService {
    private final QuestionnaireProvider questionnaireProvider;

    private final QuestionnaireFormatter printFormatter;

    public QuestionnaireService(QuestionnaireProvider questionnaireProvider, QuestionnaireFormatter printFormatter) {
        this.questionnaireProvider = questionnaireProvider;
        this.printFormatter = printFormatter;
    }

    public void execute() {
        questionnaireProvider.get().forEach(q -> {
            List<String> answers = q.answers();
            if (CollectionUtils.isEmpty(answers)) {
                return;
            }
            System.out.println(format(q));
            Scanner sc = new Scanner(System.in);
            int i;
            if (sc.hasNextInt()) {
                i = sc.nextInt();
                checkAnswer(answers.size(), i);
            } else {
                System.out.println("Value is invalid.");
            }
        });
    }

    private String format(Questionnaire questionnaire) {
        return printFormatter.format(questionnaire);
    }

    private static void checkAnswer(int size, int input) {
        if (input <= 0 || input > size) {
            System.out.println("Invalid integer value.");
        }
    }
}
