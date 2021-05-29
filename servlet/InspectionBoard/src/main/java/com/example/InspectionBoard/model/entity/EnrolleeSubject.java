package com.example.InspectionBoard.model.entity;

public class EnrolleeSubject {
    private final String name;
    private final int mark;

    public EnrolleeSubject(String name, int mark) {
        this.name = name;
        this.mark = mark;
    }

    public String getName() {
        return name;
    }

    public int getMark() {
        return mark;
    }
}
