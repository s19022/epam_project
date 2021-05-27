package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.exceptions.CannotRegisterToFacultyException;
import com.example.InspectionBoard.exceptions.NoSuchAccountException;
import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.model.service.FacultyRegistrationService;

import javax.servlet.http.HttpServletRequest;


import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;

public class RegisterToFacultyCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String enrolleeLogin = request.getParameter("enrolleeLogin");
        String facultyName = request.getParameter("facultyName");
        try {
            FacultyRegistrationService.register(enrolleeLogin, facultyName);
        } catch (NoSuchAccountException | NoSuchFacultyException | CannotRegisterToFacultyException e) {
            e.printStackTrace();
        }
        return REDIRECT_KEYWORD + AccountRole.USER.getRedirectPath();
    }
}
