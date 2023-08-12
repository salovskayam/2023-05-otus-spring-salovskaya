package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring.service.QuestionnaireRunner;

@SpringBootApplication
public class SpringBootDemo {

    public static void main(String[] args) {
        var applicationContext = SpringApplication.run(SpringBootDemo.class, args);

        var questionnaireRunner = applicationContext.getBean(QuestionnaireRunner.class);
        questionnaireRunner.execute();
    }
}
