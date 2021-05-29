package com.example.InspectionBoard.mainController.command.enrollee;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.RequestType;
import com.example.InspectionBoard.model.entity.EnrolleeSubject;
import com.example.InspectionBoard.model.service.EnrolleeSubjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EnrolleeMainCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String login = (String)request.getSession().getAttribute("login");
        System.out.println(login);
        List<EnrolleeSubject> enrolleeSubjects = EnrolleeSubjectService.findAllByEnrolleeLogin(login);
        System.out.println(enrolleeSubjects);
        request.setAttribute("subjects", enrolleeSubjects);
        return "/WEB-INF/enrollee/main.jsp";
    }
}
