package com.example.InspectionBoard.mainController.command;

import javax.servlet.http.HttpServletRequest;

public class AdminMainCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        return "/WEB-INF/admin/main.jsp";
    }
}
