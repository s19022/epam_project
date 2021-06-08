package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.RequestType;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.*;

public class CreateNewFacultySubjectCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        System.out.println("in");
        return REDIRECT_KEYWORD + "/faculties";
    }
}
