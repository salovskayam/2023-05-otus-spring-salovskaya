package ru.otus.spring.domain;

import java.util.ArrayList;
import java.util.List;

public class StudentTestResult {
    private final Student student;

    private List<Integer> answers = new ArrayList<>();

    private List<Integer> rightAnswers = new ArrayList<>();

    private boolean isSuccess;

    public StudentTestResult(Student student) {
        this.student = student;
    }

    public Student getStudent() {
        return student;
    }

    public List<Integer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Integer> answers) {
        this.answers = answers;
    }

    public List<Integer> getRightAnswers() {
        return rightAnswers;
    }

    public void setRightAnswers(List<Integer> rightAnswers) {
        this.rightAnswers = rightAnswers;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    @Override
    public String toString() {
        return "StudentTestResult{" +
                "student=" + student +
                ", answers=" + answers +
                ", rightAnswers=" + rightAnswers +
                ", isSuccess=" + isSuccess +
                '}';
    }
}
