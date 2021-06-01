package com.example.InspectionBoard.mainController.command.admin;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.RequestType;

import javax.servlet.http.HttpServletRequest;

public class AdminMainCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        return "/WEB-INF/admin/main.jsp";
    }
}
