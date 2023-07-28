package ru.otus.spring.dao;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.domain.Questionnaire;
import ru.otus.spring.exceptions.QuestionnaireException;
import ru.otus.spring.service.QuestionParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@Component
public class QuestionnaireCsvProvider implements QuestionnaireProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireCsvProvider.class);

    private final MessageSource messageSource;

    private final AppProps props;

    private final QuestionParser questionParser;

    public QuestionnaireCsvProvider(MessageSource messageSource, AppProps props, QuestionParser questionParser) {
        this.messageSource = messageSource;
        this.props = props;
        this.questionParser = questionParser;
    }

    @Override
    public List<Questionnaire> get() {
        var path = messageSource.getMessage("csv.path", null, props.getLocale());
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found");
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            return bufferedReader.lines()
                    .filter(StringUtils::isNotBlank)
                    .map(questionParser::parse)
                    .toList();
        } catch (IOException e) {
            LOGGER.error("Failed to parse file");
            throw new QuestionnaireException(e);
        }
    }

}
