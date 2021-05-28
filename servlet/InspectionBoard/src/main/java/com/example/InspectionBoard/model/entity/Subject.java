package com.example.InspectionBoard.model.entity;

public class Subject {
    private final String name;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "name='" + name + '\'' +
                '}';
    }

}
