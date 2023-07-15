package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.dao.QuestionnaireCsvProvider;
import ru.otus.spring.dao.QuestionnaireProvider;
import ru.otus.spring.domain.Questionnaire;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class QuestionnaireCsvProviderTest {

    private QuestionnaireProvider questionnaireProvider;

    private QuestionParser questionParser;

    @BeforeEach
    void setUp() {
        questionParser = new QuestionWithAnswerParser();
    }

    @Test
    void shouldReturnQuestionsEmptyList() {
        questionnaireProvider = new QuestionnaireCsvProvider("wrong_path", questionParser);

        assertThatThrownBy(() -> questionnaireProvider.get()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnQuestionsWithEmptyAnswers() {
        questionnaireProvider = new QuestionnaireCsvProvider("csv/questionsWithoutAnswers.csv", questionParser);

        List<Questionnaire> questionnaires = questionnaireProvider.get();

        assertThat(questionnaires).isNotEmpty()
                .allMatch(s -> s.answers().isEmpty());
    }

    @Test
    void shouldReturnQuestionsWithAnswers() {
        questionnaireProvider = new QuestionnaireCsvProvider("csv/questions.csv", questionParser);

        List<Questionnaire> questionnaires = questionnaireProvider.get();

        assertThat(questionnaires).isNotEmpty()
                .allMatch(s -> !s.answers().isEmpty());
    }

}