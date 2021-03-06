package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.exceptions.BudgetPlacesBiggerThanAllPlacesException;
import com.example.InspectionBoard.exceptions.FacultyNameIsTakenException;
import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.dto.CreateFacultyDto;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.model.enums.CreateNewFacultyResult.ALREADY_EXISTS;
import static com.example.InspectionBoard.model.enums.CreateNewFacultyResult.INVALID_NUMBER_OF_PLACES;

public class CreateNewFacultyCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String facultyName = (String) request.getAttribute(FACULTY_NAME);
        int budgetPlaces = (int) request.getAttribute(BUDGET_PLACES);
        int allPlaces = (int) request.getAttribute(ALL_PLACES);

        CreateFacultyDto dto = new CreateFacultyDto(facultyName, budgetPlaces, allPlaces);
        try {
            new FacultyService().create(dto);
        } catch (FacultyNameIsTakenException e) {
            request.getSession().setAttribute(CREATE_NEW_FACULTY_RESULT, ALREADY_EXISTS);
        } catch (BudgetPlacesBiggerThanAllPlacesException e) {
            request.getSession().setAttribute(CREATE_NEW_FACULTY_RESULT, INVALID_NUMBER_OF_PLACES);
        }
        return REDIRECT_KEYWORD + "/faculties";
    }
}
