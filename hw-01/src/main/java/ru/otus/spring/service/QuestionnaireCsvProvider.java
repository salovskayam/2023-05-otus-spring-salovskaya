package ru.otus.spring.service;

import org.apache.commons.lang3.StringUtils;
import ru.otus.spring.domain.Questionnaire;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionnaireCsvProvider implements QuestionnaireProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionnaireCsvProvider.class);
    private final String path;

    public QuestionnaireCsvProvider(String path) {
        this.path = path;
    }

    @Override
    public List<Questionnaire> get() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream != null) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            return bufferedReader.lines()
                    .filter(StringUtils::isNotBlank)
                    .map(QuestionParser::parse)
                    .toList();
        } else {
            LOGGER.error("Failed to parse csv file");
            return Collections.emptyList();
        }
    }

}
