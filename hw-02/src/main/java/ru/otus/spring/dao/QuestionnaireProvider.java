package ru.otus.spring.dao;

import ru.otus.spring.domain.Questionnaire;

import java.util.List;
import java.util.function.Supplier;

public interface QuestionnaireProvider extends Supplier<List<Questionnaire>> {

}
