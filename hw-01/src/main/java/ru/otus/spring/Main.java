package ru.otus.spring;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.service.QuestionnaireService;


public class Main {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");
        QuestionnaireService questionnaireService = context.getBean(QuestionnaireService.class);
        questionnaireService.execute();
        context.close();
    }
}
