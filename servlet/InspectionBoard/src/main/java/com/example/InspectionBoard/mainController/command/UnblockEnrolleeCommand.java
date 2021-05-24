package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.model.repository.AccountRepository;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;
import static com.example.InspectionBoard.model.enums.AccountRole.ADMIN;

public class UnblockEnrolleeCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String login = request.getParameter("login");
        AccountRepository.getInstance().unblockEnrollee(login);
        return REDIRECT_KEYWORD + ADMIN.getRedirectPath();
    }
}
