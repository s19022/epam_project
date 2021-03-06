package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.exceptions.BudgetPlacesBiggerThanAllPlacesException;
import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.dto.ModifyFacultyDto;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.Constants.ALL_PLACES;
import static com.example.InspectionBoard.model.enums.CreateNewFacultyResult.INVALID_NUMBER_OF_PLACES;

public class ModifyFacultyCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String facultyName = (String) request.getAttribute(FACULTY_NAME);
        int budgetPlaces = (int) request.getAttribute(BUDGET_PLACES);
        int allPlaces = (int) request.getAttribute(ALL_PLACES);

        ModifyFacultyDto dto = new ModifyFacultyDto(facultyName, budgetPlaces, allPlaces);
        try {
            new FacultyService().update(dto);
        } catch (BudgetPlacesBiggerThanAllPlacesException e) {
            System.out.println("in");
            request.getSession().setAttribute(UPDATE_FACULTY_RESULT, INVALID_NUMBER_OF_PLACES);
        }
        return REDIRECT_KEYWORD + "/faculties";
    }
}
