package com.example.InspectionBoard.mainController.command.admin;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.AccountService;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.LOGIN;
import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;
import static com.example.InspectionBoard.model.enums.AccountRole.ADMIN;

public class UnblockEnrolleeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String login = request.getParameter(LOGIN);
        new AccountService().unblockEnrollee(login);
        return REDIRECT_KEYWORD + "/admin/enrollee";
    }
}
