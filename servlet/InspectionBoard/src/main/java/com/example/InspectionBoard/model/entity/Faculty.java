package com.example.InspectionBoard.model.entity;


import java.util.List;
import java.util.Objects;

public class Faculty {
    private final int id;
    private final String name;
    private final int budgetPlaces;
    private final int allPlaces;
    private final List<RequiredSubject> requiredSubjects;

    public Faculty(int id, String name, int budgetPlaces, int allPlaces, List<RequiredSubject> requiredSubjects) {
        this.id = id;
        this.name = name;
        this.budgetPlaces = budgetPlaces;
        this.allPlaces = allPlaces;
        this.requiredSubjects = requiredSubjects;
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

    public List<RequiredSubject> getRequiredSubjects() {
        return requiredSubjects;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return id == faculty.id && budgetPlaces == faculty.budgetPlaces && allPlaces == faculty.allPlaces && name.equals(faculty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, budgetPlaces, allPlaces);
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", budgetPlaces=" + budgetPlaces +
                ", allPlaces=" + allPlaces +
                ", requiredSubjects=" + requiredSubjects +
                '}';
    }
}
