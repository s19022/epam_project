package com.example.InspectionBoard.model.DTO;

import com.example.InspectionBoard.exceptions.ValidationException;

public class DtoUtils {
    public static void validateString(String toCheck) throws ValidationException {
        if(toCheck == null || toCheck.trim().isEmpty()){
            throw new ValidationException("Not valid");
        }
    }

    public static void validate(String... items) throws ValidationException {
        for (String item : items){
            validateString(item);
        }
    }

}
