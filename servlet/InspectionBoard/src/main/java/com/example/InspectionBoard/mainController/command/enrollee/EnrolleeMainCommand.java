package com.example.InspectionBoard.mainController.command.enrollee;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.entity.EnrolleeSubject;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.service.EnrolleeSubjectService;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EnrolleeMainCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String login = (String)request.getSession().getAttribute("login");

        List<EnrolleeSubject> enrolleeSubjects = new EnrolleeSubjectService().findAllByEnrolleeLogin(login);
        List<Faculty> registeredFaculties = new FacultyService().findByEnrolleeLogin(login);

        request.setAttribute("subjects", enrolleeSubjects);
        request.setAttribute("registeredFaculties", registeredFaculties);
        return "/WEB-INF/enrollee/main.jsp";
    }
}
