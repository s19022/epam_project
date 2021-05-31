package com.example.InspectionBoard.model.dto.db;

public class DbEnrolleeDto {
    private final int id;
    private final String login;
    private final boolean isBlocked;
    private final String firstName;
    private final String lastName;
    private final String fatherName;
    private final String email;
    private final String city;
    private final String region;
    private final String schoolName;

    public DbEnrolleeDto(int id, String login, boolean isBlocked, String firstName,
                         String lastName, String fatherName, String email, String city,
                         String region, String schoolName) {
        this.id = id;
        this.login = login;
        this.isBlocked = isBlocked;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fatherName = fatherName;
        this.email = email;
        this.city = city;
        this.region = region;
        this.schoolName = schoolName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getEmail() {
        return email;
    }

    public String getCity() {
        return city;
    }

    public String getRegion() {
        return region;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public String getLogin() {
        return login;
    }

    public boolean isBlocked() {
        return isBlocked;
    }
}
