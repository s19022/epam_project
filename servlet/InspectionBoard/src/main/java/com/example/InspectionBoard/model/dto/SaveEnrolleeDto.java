package com.example.InspectionBoard.model.dto;


import com.example.InspectionBoard.exceptions.ValidationException;

import java.util.Arrays;

import static com.example.InspectionBoard.model.dto.DtoUtils.validate;

public class SaveEnrolleeDto {
    private final String login;
    private final String password;
    private final String firstName;
    private final String fatherName;
    private final String lastName;
    private final String email;
    private final String city;
    private final String region;
    private final String schoolName;
    private final byte[] certificateScan;

    private SaveEnrolleeDto(String login, String password, String firstName, String fatherName, String lastName,
                            String email, String city, String region, String schoolName, byte[] certificateScan) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.fatherName = fatherName;
        this.lastName = lastName;
        this.email = email;
        this.city = city;
        this.region = region;
        this.schoolName = schoolName;
        this.certificateScan = certificateScan;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public String getLastName() {
        return lastName;
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

    public byte[] getCertificateScan() {
        return certificateScan;
    }

    //was forbidden to use @NotNull for some reason
    public static SaveEnrolleeDto getInstance(
            String login, String password, String firstName, String fatherName, String lastName,
            String email, String city, String region, String schoolName, byte[] certificateScan)
            throws ValidationException{
        validate(login, password, firstName, fatherName, lastName, email, city, region, schoolName);
        return new SaveEnrolleeDto(login, password, firstName, fatherName,
                lastName, email, city, region, schoolName, certificateScan);
    }
    @Override
    public String toString() {
        return "SaveEnrollee{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", fatherName='" + fatherName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", region='" + region + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", certificateScan=" + Arrays.toString(certificateScan) +
                '}';
    }
}
