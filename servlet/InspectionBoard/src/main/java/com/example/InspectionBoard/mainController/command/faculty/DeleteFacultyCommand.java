package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.RequestType;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;

public class DeleteFacultyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String facultyName = request.getParameter("facultyName");
        FacultyService.deleteByFacultyName(facultyName);
        return "/faculties";
    }
}
