package ru.otus.spring;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.otus.spring.config.AppConfig;
import ru.otus.spring.service.QuestionnaireRunner;


public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        QuestionnaireRunner questionnaireRunner = context.getBean(QuestionnaireRunner.class);
        questionnaireRunner.execute();
    }
}
