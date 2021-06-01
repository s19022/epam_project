package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.model.enums.RequestType;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.mainController.command.CommandUtility.isLoggedIn;


public class LogoutCommand implements Command{
    @SuppressWarnings("unchecked")
    @Override
    public String execute(HttpServletRequest request, RequestType requestType) {
        ServletContext context = request.getServletContext();
        HttpSession session = request.getSession();
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute(LOGGED_USERS);
        String login  = (String) session.getAttribute("login");
        if (isLoggedIn(loggedUsers, login)){
            loggedUsers.remove(login);
            context.setAttribute(LOGGED_USERS, loggedUsers);
            session.setAttribute("userRole", AccountRole.UNKNOWN);
        }
        return REDIRECT_KEYWORD + AccountRole.UNKNOWN.getRedirectPath();
    }
}
