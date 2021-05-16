package com.example.InspectionBoard.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Faculty {
    private final int id;
    private final String name;
    private final int budgetPlaces;
    private final int allPlaces;
}
