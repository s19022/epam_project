package com.example.InspectionBoard.model.dto.parse;

public class ParseEnrolleeSubjectDto {
    private final int id;
    private final int mark;

    public ParseEnrolleeSubjectDto(int id, int mark) {
        this.id = id;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }

    public int getMark() {
        return mark;
    }
}
