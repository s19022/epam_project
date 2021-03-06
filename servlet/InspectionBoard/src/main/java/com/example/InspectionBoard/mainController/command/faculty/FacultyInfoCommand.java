package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.entity.Subject;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.service.FacultyService;
import com.example.InspectionBoard.model.service.SubjectService;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

import static com.example.InspectionBoard.Constants.*;

public class FacultyInfoCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String facultyName = getFacultyName(request);
        try {
            Faculty f = new FacultyService().findByName(facultyName);
            List<Subject> notTakenSubjects = new SubjectService().findNotTakenByFacultyName(facultyName);
            request.getSession().setAttribute(FACULTY_INFO, f);
            request.setAttribute(NOT_TAKEN_SUBJECTS, notTakenSubjects);
            request.getSession().setAttribute(FACULTY_NAME, facultyName);
        } catch (NoSuchFacultyException e) {
            e.printStackTrace();
        }
        return "/WEB-INF/faculty/info.jsp";
    }

    private String getFacultyName(HttpServletRequest request){
        String facultyName = request.getParameter(FACULTY_NAME);
        if (facultyName != null){
            return facultyName;
        }
        return (String) request.getSession().getAttribute(FACULTY_NAME);
    }
}
