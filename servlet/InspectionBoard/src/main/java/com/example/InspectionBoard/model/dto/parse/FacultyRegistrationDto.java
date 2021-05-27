package com.example.InspectionBoard.model.dto.parse;

public class FacultyRegistrationDto {
    private final int enrolleeId;
    private final int facultyId;

    public FacultyRegistrationDto(int enrolleeId, int facultyId) {
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
