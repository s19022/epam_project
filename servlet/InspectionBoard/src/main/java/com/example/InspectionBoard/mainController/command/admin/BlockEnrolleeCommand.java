package com.example.InspectionBoard.mainController.command.admin;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.AccountService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import java.util.HashSet;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.model.enums.AccountRole.ADMIN;

public class BlockEnrolleeCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String login = request.getParameter(LOGIN);
        new AccountService().blockEnrollee(login);
        removeAccountFromContext(request.getServletContext(), login);
        return REDIRECT_KEYWORD + ADMIN.getRedirectPath();
    }

    @SuppressWarnings("unchecked")
    private void removeAccountFromContext(ServletContext context, String login){
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute(LOGGED_USERS);
        loggedUsers.remove(login);
        context.setAttribute(LOGGED_USERS, loggedUsers);
    }
}
