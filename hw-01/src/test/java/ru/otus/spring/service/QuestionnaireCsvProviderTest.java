package ru.otus.spring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.otus.spring.domain.Questionnaire;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class QuestionnaireCsvProviderTest {

    private QuestionnaireProvider questionnaireProvider;

    @ParameterizedTest
    @ValueSource(strings = {"wrong path", "csv/blankFile.csv"})
    void shouldReturnQuestionsEmptyList(String path) {
        questionnaireProvider = new QuestionnaireCsvProvider(path);

        List<Questionnaire> questionnaires = questionnaireProvider.get();

        assertThat(questionnaires).isEmpty();
    }

    @Test
    void shouldReturnQuestionsWithEmptyAnswers() {
        questionnaireProvider = new QuestionnaireCsvProvider("csv/questionsWithoutAnswers.csv");

        List<Questionnaire> questionnaires = questionnaireProvider.get();

        assertThat(questionnaires).isNotEmpty()
                .allMatch(s -> s.answers().isEmpty());
    }

    @Test
    void shouldReturnQuestionsWithAnswers() {
        questionnaireProvider = new QuestionnaireCsvProvider("csv/questions.csv");

        List<Questionnaire> questionnaires = questionnaireProvider.get();

        assertThat(questionnaires).isNotEmpty()
                .allMatch(s -> !s.answers().isEmpty());
    }

}