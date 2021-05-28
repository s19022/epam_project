package com.example.InspectionBoard.model.dto.db;

public class DbSubjectDto {
    private final int id;
    private final String name;

    public DbSubjectDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
