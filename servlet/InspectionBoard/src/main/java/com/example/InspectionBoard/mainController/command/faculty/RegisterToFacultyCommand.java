package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.exceptions.CannotRegisterToFacultyException;
import com.example.InspectionBoard.exceptions.NoSuchAccountException;
import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.RequestType;
import com.example.InspectionBoard.model.service.FacultyRegistrationService;

import javax.servlet.http.HttpServletRequest;

public class RegisterToFacultyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String enrolleeLogin = request.getParameter("enrolleeLogin");
        String facultyName = request.getParameter("facultyName");
        try {
            FacultyRegistrationService.register(enrolleeLogin, facultyName);
            request.setAttribute("facultyRegistrationStatus", "Success!");
        } catch (NoSuchAccountException | NoSuchFacultyException | CannotRegisterToFacultyException e) {
            request.setAttribute("facultyRegistrationStatus", e);
        }
        return "/faculty/info.jsp";
    }
}
