package ru.otus.spring.exceptions;

public class QuestionnaireException extends RuntimeException {
    public QuestionnaireException() {
        super();
    }

    public QuestionnaireException(String message) {
        super(message);
    }

    public QuestionnaireException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuestionnaireException(Throwable cause) {
        super(cause);
    }
}
