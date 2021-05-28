package com.example.InspectionBoard.model.dto.db;

import com.example.InspectionBoard.model.entity.Account;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.entity.RequiredSubject;
import com.example.InspectionBoard.model.entity.Subject;

import java.util.List;
import java.util.stream.Collectors;

public final class Mapper {
    public static Account toAccount(DbAccountDto dto){
        return new Account(dto.getRole(), dto.getLogin()/*, dto.isBlocked()*/);
    }

    public static List<Faculty> toFaculty(List<DbFacultyDto> dto){
        return dto.stream().map(Mapper::toFaculty).collect(Collectors.toList());
    }

    public static Faculty toFaculty(DbFacultyDto dto){
        int id = dto.getId();
        String name = dto.getName();
        int budgetPlaces = dto.getBudgetPlaces();
        int allPlaces = dto.getAllPlaces();
        List<DbRequiredSubjectDto> requiredSubjectsDto = dto.getRequiredSubject();
        List<RequiredSubject> requiredSubjects = requiredSubjectsDto.stream()
                .map(Mapper::toRequiredSubject).collect(Collectors.toList());
        return new Faculty(id, name, budgetPlaces, allPlaces, requiredSubjects);
    }

    public static RequiredSubject toRequiredSubject(DbRequiredSubjectDto dto){
        return new RequiredSubject(new Subject(dto.getId(), dto.getName()), dto.getMinimalGrade());
    }


    public static List<Subject> toSubject(List<DbSubjectDto> dto){
        return dto.stream().map(Mapper::toSubject).collect(Collectors.toList());
    }

    public static Subject toSubject(DbSubjectDto dto){
        return new Subject(dto.getId(), dto.getName());
    }
}
