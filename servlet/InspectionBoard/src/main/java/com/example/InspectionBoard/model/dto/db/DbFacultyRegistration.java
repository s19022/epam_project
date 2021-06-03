package com.example.InspectionBoard.model.dto.db;

public class DbFacultyRegistration {
    private final int facultyRegistrationId;
    private final String statusName;
    private final int enrolleeId;
    private final String enrolleeLogin;
    private final int facultyId;
    private final String facultyName;

    public DbFacultyRegistration(int facultyRegistrationId, String statusName, int enrolleeId, String enrolleeLogin, int facultyId, String facultyName) {
        this.facultyRegistrationId = facultyRegistrationId;
        this.statusName = statusName;
        this.enrolleeId = enrolleeId;
        this.enrolleeLogin = enrolleeLogin;
        this.facultyId = facultyId;
        this.facultyName = facultyName;
    }

    public int getFacultyRegistrationId() {
        return facultyRegistrationId;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getEnrolleeId() {
        return enrolleeId;
    }

    public String getEnrolleeLogin() {
        return enrolleeLogin;
    }

    public int getFacultyId() {
        return facultyId;
    }

    public String getFacultyName() {
        return facultyName;
    }
}
