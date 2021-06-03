package com.example.InspectionBoard.model.entity;

import com.example.InspectionBoard.model.enums.RegistrationStatus;

public class FacultyRegistration {
    private final String enrolleeLogin;
    private final String facultyName;
    private final RegistrationStatus status;

    public FacultyRegistration(String enrolleeLogin, String facultyName, RegistrationStatus status) {
        this.enrolleeLogin = enrolleeLogin;
        this.facultyName = facultyName;
        this.status = status;
    }

    public String getEnrolleeLogin() {
        return enrolleeLogin;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public RegistrationStatus getStatus() {
        return status;
    }
}
