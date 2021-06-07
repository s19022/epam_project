package com.example.InspectionBoard.model.dto;

public class CreateEnrolleeSubjectDto {
    private final String name;
    private final int mark;

    public CreateEnrolleeSubjectDto(String name, int mark) {
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
