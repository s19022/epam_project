package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.RequestType;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.DEFAULT_PATH;
import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;

public class FacultyInfoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String facultyName = request.getParameter("name");
        System.out.println(facultyName);
        return REDIRECT_KEYWORD + DEFAULT_PATH;
    }
}
