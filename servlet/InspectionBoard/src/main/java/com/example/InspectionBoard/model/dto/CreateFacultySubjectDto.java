package com.example.InspectionBoard.model.dto;

public class CreateFacultySubjectDto {
    private final String subjectName;
    private final String facultyName;
    private final int minimalMark;

    public CreateFacultySubjectDto(String subjectName, String facultyName, int minimalMark) {
        this.subjectName = subjectName;
        this.facultyName = facultyName;
        this.minimalMark = minimalMark;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public int getMinimalMark() {
        return minimalMark;
    }
}
