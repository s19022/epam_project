package com.example.InspectionBoard.model.dto.db;

public class DbRequiredSubjectDto {
    private final int id;
    private final String name;
    private final int minimalGrade;

    public DbRequiredSubjectDto(int id, String name, int minimalGrade) {
        this.id = id;
        this.name = name;
        this.minimalGrade = minimalGrade;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getMinimalGrade() {
        return minimalGrade;
    }
}
