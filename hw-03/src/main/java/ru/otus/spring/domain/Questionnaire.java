package ru.otus.spring.domain;


import java.util.List;

public record Questionnaire(String question, List<String> answers) {

}
