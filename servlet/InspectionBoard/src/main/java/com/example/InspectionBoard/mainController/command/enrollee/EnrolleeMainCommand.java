package com.example.InspectionBoard.mainController.command.enrollee;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.entity.EnrolleeSubject;
import com.example.InspectionBoard.model.entity.FacultyRegistration;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.EnrolleeSubjectService;
import com.example.InspectionBoard.model.service.FacultyRegistrationService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.InspectionBoard.Constants.*;

public class EnrolleeMainCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String login = (String)request.getSession().getAttribute(LOGIN);

        List<EnrolleeSubject> enrolleeSubjects = new EnrolleeSubjectService().findAllByEnrolleeLogin(login);
        List<FacultyRegistration> facultyRegistrationList = new FacultyRegistrationService().findByEnrolleeLogin(login);
        request.setAttribute(SUBJECTS, enrolleeSubjects);
        request.setAttribute(REGISTERED_FACULTIES, facultyRegistrationList);
        return "/WEB-INF/enrollee/main.jsp";
    }
}
