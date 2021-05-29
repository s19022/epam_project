package com.example.InspectionBoard.model.dto.db;

public class DbEnrolleeSubjectDto {
    private final int id;
    private final String name;
    private final int mark;

    public DbEnrolleeSubjectDto(int id, String name, int mark) {
        this.id = id;
        this.name = name;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }

    public String getName() {
        return name;
    }
}
