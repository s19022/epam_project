package com.example.InspectionBoard.mainController.command.enrollee;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.RequestType;
import com.example.InspectionBoard.model.entity.Subject;
import com.example.InspectionBoard.model.service.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class EnrolleeMainCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        List<Subject> subjects = SubjectService.findAll();
        request.getSession().setAttribute("myList", subjects);             //fixme
        return "/enrollee/main.jsp";
    }
}
