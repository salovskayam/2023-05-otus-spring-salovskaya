package ru.otus.spring.service.utils;

import ru.otus.spring.exceptions.QuestionnaireException;

public class QuestionnaireUtil {

    private QuestionnaireUtil() {
        throw new UnsupportedOperationException();
    }

    public static void checkAnswer(int size, int input) {
        if (input <= 0 || input > size) {
            throw new QuestionnaireException("Given number of answer is out of range");
        }
    }
}
