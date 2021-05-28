package com.example.InspectionBoard.model.entity;

public class RequiredSubject {
    private final String name;
    private final int minimalGrade;

    public RequiredSubject(String name, int minimalGrade) {
        this.name = name;
        this.minimalGrade = minimalGrade;
    }

    public String getName() {
        return name;
    }


    public int getMinimalGrade() {
        return minimalGrade;
    }

    @Override
    public String toString() {
        return "RequiredSubject{" +
                "name='" + name + '\'' +
                ", minimalGrade=" + minimalGrade +
                '}';
    }
}
