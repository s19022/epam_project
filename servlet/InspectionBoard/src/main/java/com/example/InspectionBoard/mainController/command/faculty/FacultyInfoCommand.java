package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.RequestType;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;

public class FacultyInfoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String facultyName = request.getParameter("name");
        try {
            Faculty f =  FacultyService.getByName(facultyName);
            request.getSession().setAttribute("facultyInfo", f);
        } catch (NoSuchFacultyException e) {
            e.printStackTrace();
        }
        return "/faculty/info.jsp";
    }
}
