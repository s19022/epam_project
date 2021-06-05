package com.example.InspectionBoard.model.dto;

public class ModifyFacultyDto {
    private final String name;
    private final int budgetPlaces;
    private final int allPlaces;

    public ModifyFacultyDto(String name, int budgetPlaces, int allPlaces) {
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
