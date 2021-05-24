package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.implementation.JDBCAccountDao;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;
import static com.example.InspectionBoard.model.enums.AccountRole.ADMIN;

public class BlockEnrolleeCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String login = request.getParameter("login");
        DaoFactory.getInstance().createAccountDao().blockEnrollee(login);
        return REDIRECT_KEYWORD + ADMIN.getRedirectPath();
    }
}
