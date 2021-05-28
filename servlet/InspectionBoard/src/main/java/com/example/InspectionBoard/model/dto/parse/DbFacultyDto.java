package com.example.InspectionBoard.model.dto.parse;

import java.util.Collections;
import java.util.List;

public class DbFacultyDto {
    private final int id;
    private final String name;
    private final int budgetPlaces;
    private final int allPlaces;
    private final List<DbRequiredSubjectDto> requiredSubject;

    public DbFacultyDto(int id, String name, int budgetPlaces, int allPlaces, List<DbRequiredSubjectDto> requiredSubject) {
        this.id = id;
        this.name = name;
        this.budgetPlaces = budgetPlaces;
        this.allPlaces = allPlaces;
        this.requiredSubject = requiredSubject;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBudgetPlaces() {
        return budgetPlaces;
    }

    public int getAllPlaces() {
        return allPlaces;
    }

    public List<DbRequiredSubjectDto> getRequiredSubject() {
        return Collections.unmodifiableList(requiredSubject);
    }
}
