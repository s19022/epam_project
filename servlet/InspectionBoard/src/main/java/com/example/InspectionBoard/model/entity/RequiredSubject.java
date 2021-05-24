package com.example.InspectionBoard.model.entity;

public class RequiredSubject {
    private final Subject subject;
    private final int minimalGrade;

    public RequiredSubject(Subject subject, int minimalGrade) {
        this.subject = subject;
        this.minimalGrade = minimalGrade;
    }

    public Subject getSubject() {
        return subject;
    }

    public int getMinimalGrade() {
        return minimalGrade;
    }

    @Override
    public String toString() {
        return "RequiredSubject{" +
                "subject=" + subject +
                ", minimalGrade=" + minimalGrade +
                '}';
    }
}
