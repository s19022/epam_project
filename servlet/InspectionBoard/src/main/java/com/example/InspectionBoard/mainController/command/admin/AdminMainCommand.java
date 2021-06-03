package com.example.InspectionBoard.mainController.command.admin;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.entity.FacultyRegistration;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.FacultyRegistrationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.InspectionBoard.Constants.REGISTRATION_LIST;

public class AdminMainCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        List<FacultyRegistration> registrationList = new FacultyRegistrationService().findAll();
        request.setAttribute(REGISTRATION_LIST, registrationList);
        return "/WEB-INF/admin/main.jsp";
    }
}
