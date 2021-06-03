package com.example.InspectionBoard.model.dto;

public class SaveFacultyRegistrationDto {
    private final int enrolleeId;
    private final int facultyId;

    public SaveFacultyRegistrationDto(int enrolleeId, int facultyId) {
        this.enrolleeId = enrolleeId;
        this.facultyId = facultyId;
    }

    public int getEnrolleeId() {
        return enrolleeId;
    }

    public int getFacultyId() {
        return facultyId;
    }
}
