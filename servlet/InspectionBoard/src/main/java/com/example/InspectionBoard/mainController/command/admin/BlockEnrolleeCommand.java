package com.example.InspectionBoard.mainController.command.admin;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.RequestType;
import com.example.InspectionBoard.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;
import static com.example.InspectionBoard.model.enums.AccountRole.ADMIN;

public class BlockEnrolleeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String login = request.getParameter("login");
        AccountService.blockEnrollee(login);
        //fixme invalidate session
        return REDIRECT_KEYWORD + ADMIN.getRedirectPath();
    }
}