package ru.otus.spring.service;

import org.springframework.util.CollectionUtils;
import ru.otus.spring.domain.Questionnaire;

import java.util.List;
import java.util.Scanner;

public class ConsoleQuestionnaireService implements QuestionnaireService {
    private final QuestionnaireProvider questionnaireProvider;

    private final QuestionnaireFormatter printFormatter;

    public ConsoleQuestionnaireService(QuestionnaireProvider questionnaireProvider,
                                       QuestionnaireFormatter printFormatter) {
        this.questionnaireProvider = questionnaireProvider;
        this.printFormatter = printFormatter;
    }

    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        questionnaireProvider.get().forEach(q -> {
            List<String> answers = q.answers();
            if (CollectionUtils.isEmpty(answers)) {
                return;
            }
            System.out.println(format(q));
            int i;
            if (sc.hasNextInt()) {
                i = sc.nextInt();
                checkAnswer(answers.size(), i);
            } else {
                String input = sc.next();
                System.out.println("Value is invalid: " + input);
            }
        });
    }

    private String format(Questionnaire questionnaire) {
        return printFormatter.format(questionnaire);
    }

    private void checkAnswer(int size, int input) {
        if (input <= 0 || input > size) {
            System.out.println("Invalid integer value.");
        }
    }
}
