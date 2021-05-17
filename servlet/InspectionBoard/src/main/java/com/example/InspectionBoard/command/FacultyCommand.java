package com.example.InspectionBoard.command;

import javax.servlet.http.HttpServletRequest;

public class FacultyCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
        //todo
    }
}
