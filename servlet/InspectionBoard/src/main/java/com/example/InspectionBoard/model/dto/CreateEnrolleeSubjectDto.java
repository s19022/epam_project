package com.example.InspectionBoard.model.dto;

public class CreateEnrolleeSubjectDto {
    private final String subjectName;
    private final String enrolleeName;
    private final int mark;

    public CreateEnrolleeSubjectDto(String subjectName, String enrolleeName, int mark) {
        this.subjectName = subjectName;
        this.enrolleeName = enrolleeName;
        this.mark = mark;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getMark() {
        return mark;
    }

    public String getEnrolleeName() {
        return enrolleeName;
    }
}
