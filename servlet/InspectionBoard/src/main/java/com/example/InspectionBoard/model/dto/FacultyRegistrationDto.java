package com.example.InspectionBoard.model.dto;

public class FacultyRegistrationDto {
    private final String enrolleeLogin;
    private final String facultyName;

    public FacultyRegistrationDto(String enrolleeLogin, String faculyName) {
        this.enrolleeLogin = enrolleeLogin;
        this.facultyName = faculyName;
    }

    public String getEnrolleeLogin() {
        return enrolleeLogin;
    }

    public String getFacultyName() {
        return facultyName;
    }
}
