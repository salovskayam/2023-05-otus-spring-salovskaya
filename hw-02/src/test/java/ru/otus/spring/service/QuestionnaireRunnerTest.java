package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionnaireProvider;
import ru.otus.spring.domain.Student;
import ru.otus.spring.domain.StudentTestResult;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QuestionnaireRunnerTest {

    @Captor
    private ArgumentCaptor<StudentTestResult> studentTestResultCaptor;

    @Mock
    private QuestionnaireProvider questionnaireProvider;

    @Mock
    private QuestionnaireService questionnaireService;

    @InjectMocks
    private QuestionnaireRunner questionnaireRunner;

    @BeforeEach
    void setUp() {
        var student = new Student("test", "test");
        var studentTestResult = new StudentTestResult(student);
        when(questionnaireService.initTesting()).thenReturn(studentTestResult);
        when(questionnaireService.getStudentAnswers(anyList())).thenReturn(Collections.emptyList());
        doNothing().when(questionnaireService).finishTesting(any(StudentTestResult.class));
        when(questionnaireProvider.get()).thenReturn(Collections.emptyList());
    }

    @Test
    void execute() {
        questionnaireRunner.execute();

        verify(questionnaireService).initTesting();
        verify(questionnaireService).getStudentAnswers(anyList());
        verify(questionnaireService).finishTesting(studentTestResultCaptor.capture());

        var studentTestResult = studentTestResultCaptor.getValue();
        assertThat(studentTestResult).extracting("isSuccess")
                .isEqualTo(false);
        assertThat(studentTestResult).extracting("student.surname")
                .isEqualTo("test");
        assertThat(studentTestResult).extracting("student.name")
                .isEqualTo("test");
    }
}