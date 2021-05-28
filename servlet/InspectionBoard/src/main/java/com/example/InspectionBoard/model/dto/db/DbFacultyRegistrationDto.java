package com.example.InspectionBoard.model.dto.db;

public class DbFacultyRegistrationDto {
    private final int enrolleeId;
    private final int facultyId;

    public DbFacultyRegistrationDto(int enrolleeId, int facultyId) {
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
