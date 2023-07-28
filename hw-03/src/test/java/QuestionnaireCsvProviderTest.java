import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import ru.otus.spring.configs.AppConfig;
import ru.otus.spring.configs.AppProps;
import ru.otus.spring.dao.QuestionnaireProvider;
import ru.otus.spring.domain.Questionnaire;

import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest()
class QuestionnaireCsvProviderTest {

    @ComponentScan({"ru.otus.spring.dao", "ru.otus.spring.service"})
    @Import(AppConfig.class)
    @Configuration
    static class NestedConfiguration {
        @Bean
        MessageSource messageSource() {
            var resourceBundleMessageSource = new ResourceBundleMessageSource();
            resourceBundleMessageSource.setBasename("i18n/apppaths");
            return resourceBundleMessageSource;
        }
    }

    @MockBean
    AppProps appProps;

    @Autowired
    private QuestionnaireProvider questionnaireProvider;

    @Test
    void shouldReturnQuestionsWithAnswersIfLocaleRu() {
        when(appProps.getLocale()).thenReturn(new Locale("ru"));

        List<Questionnaire> questionnaires = questionnaireProvider.get();

        assertThat(questionnaires).isNotEmpty()
                .allMatch(s -> !s.answers().isEmpty());
    }

    @Test
    void shouldReturnQuestionsWithAnswersIfLocaleEn() {
        when(appProps.getLocale()).thenReturn(new Locale("en"));

        List<Questionnaire> questionnaires = questionnaireProvider.get();

        assertThat(questionnaires).isNotEmpty()
                .allMatch(s -> !s.answers().isEmpty());
    }

}