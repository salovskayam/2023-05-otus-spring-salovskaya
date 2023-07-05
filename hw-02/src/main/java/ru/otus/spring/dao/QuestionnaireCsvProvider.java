package ru.otus.spring.dao;

import org.apache.commons.lang3.StringUtils;
import ru.otus.spring.domain.Questionnaire;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.spring.exceptions.QuestionnaireException;
import ru.otus.spring.service.QuestionParser;

public class QuestionnaireCsvProvider implements QuestionnaireProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireCsvProvider.class);

    private final String path;

    private final QuestionParser questionParser;

    public QuestionnaireCsvProvider(String path, QuestionParser questionParser) {
        this.path = path;
        this.questionParser = questionParser;
    }

    @Override
    public List<Questionnaire> get() {
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
