package com.example.InspectionBoard.model.dto.db;

public class DbEnrolleeSubjectDto {
    private final int id;
    private final int mark;

    public DbEnrolleeSubjectDto(int id, int mark) {
        this.id = id;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }
}
