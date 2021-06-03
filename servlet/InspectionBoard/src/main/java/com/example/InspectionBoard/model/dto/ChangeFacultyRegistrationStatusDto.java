package com.example.InspectionBoard.model.dto;

import com.example.InspectionBoard.model.enums.RegistrationStatus;

public class ChangeFacultyRegistrationStatusDto {
    private final String enrolleeLogin;
    private final String facultyName;
    private final RegistrationStatus newStatus;

    public ChangeFacultyRegistrationStatusDto(String enrolleeLogin, String facultyName, RegistrationStatus newStatus) {
        this.enrolleeLogin = enrolleeLogin;
        this.facultyName = facultyName;
        this.newStatus = newStatus;
    }

    public String getEnrolleeLogin() {
        return enrolleeLogin;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public RegistrationStatus getNewStatus() {
        return newStatus;
    }
}
