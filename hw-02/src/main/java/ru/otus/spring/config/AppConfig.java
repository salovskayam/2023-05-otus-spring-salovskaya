package ru.otus.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.dao.QuestionnaireCsvProvider;
import ru.otus.spring.dao.QuestionnaireProvider;
import ru.otus.spring.service.IOQuestionnaireService;
import ru.otus.spring.service.QuestionParser;
import ru.otus.spring.service.QuestionWithAnswerParser;
import ru.otus.spring.service.QuestionnaireFormatter;
import ru.otus.spring.service.QuestionnairePrintFormatter;
import ru.otus.spring.service.QuestionnaireRunner;
import ru.otus.spring.service.QuestionnaireService;
import ru.otus.spring.service.io.IOService;
import ru.otus.spring.service.io.IOServiceStreams;

@PropertySource("classpath:application.properties")
@Configuration
public class AppConfig {

    @Bean
    QuestionParser questionParser() {
        return new QuestionWithAnswerParser();
    }

    @Bean
    QuestionnaireProvider questionnaireProvider(@Value("${csv.path}") String path, QuestionParser questionParser) {
        return new QuestionnaireCsvProvider(path, questionParser);
    }

    @Bean
    QuestionnaireFormatter questionnaireFormatter() {
        return new QuestionnairePrintFormatter();
    }

    @Bean
    IOService ioService() {
        return new IOServiceStreams(System.out, System.in);
    }

    @Bean
    QuestionnaireService questionnaireService() {
        return new IOQuestionnaireService(ioService(), questionnaireFormatter());
    }

    @Bean
    QuestionnaireRunner questionnaireRunner(QuestionnaireProvider questionnaireProvider,
                                            QuestionnaireService questionnaireService) {
        return new QuestionnaireRunner(
                questionnaireProvider,
                questionnaireService);
    }
}
