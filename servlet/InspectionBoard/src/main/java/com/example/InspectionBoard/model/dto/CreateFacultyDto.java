package com.example.InspectionBoard.model.dto;

public class CreateFacultyDto {
    private final String name;
    private final int budgetPlaces;
    private final int allPlaces;

    public CreateFacultyDto(String name, int budgetPlaces, int allPlaces) {
        this.name = name;
        this.budgetPlaces = budgetPlaces;
        this.allPlaces = allPlaces;
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
}
