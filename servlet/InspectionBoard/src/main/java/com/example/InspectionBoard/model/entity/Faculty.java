package com.example.InspectionBoard.model.entity;


import java.util.List;
import java.util.Objects;

public class Faculty {
    private final String name;
    private final int budgetPlaces;
    private final int allPlaces;
    private final List<RequiredSubject> requiredSubjects;

    public Faculty(String name, int budgetPlaces, int allPlaces, List<RequiredSubject> requiredSubjects) {
        this.name = name;
        this.budgetPlaces = budgetPlaces;
        this.allPlaces = allPlaces;
        this.requiredSubjects = requiredSubjects;
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

    public List<RequiredSubject> getRequiredSubjects() {
        return requiredSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return getBudgetPlaces() == faculty.getBudgetPlaces() && getAllPlaces() == faculty.getAllPlaces() && Objects.equals(getName(), faculty.getName()) && Objects.equals(getRequiredSubjects(), faculty.getRequiredSubjects());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getBudgetPlaces(), getAllPlaces(), getRequiredSubjects());
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "name='" + name + '\'' +
                ", budgetPlaces=" + budgetPlaces +
                ", allPlaces=" + allPlaces +
//                ", requiredSubjects=" + requiredSubjects +
                '}';
    }
}
