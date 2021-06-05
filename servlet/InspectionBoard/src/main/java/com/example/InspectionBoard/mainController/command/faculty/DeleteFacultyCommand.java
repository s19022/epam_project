package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.FACULTY_NAME;

public class DeleteFacultyCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String facultyName = request.getParameter(FACULTY_NAME);
        new FacultyService().deleteByFacultyName(facultyName);
        return "/faculties";
    }
}
