package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.mainController.command.CommandUtility.isLoggedIn;


public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType requestType) {
        ServletContext context = request.getServletContext();
        HttpSession session = request.getSession();
        HashSet<Integer> loggedUsers = (HashSet<Integer>) context.getAttribute(LOGGED_USERS);
        Integer userId = (Integer) session.getAttribute("id");
        if (isLoggedIn(loggedUsers, userId)){
            loggedUsers.remove(userId);
            context.setAttribute(LOGGED_USERS, loggedUsers);
            session.setAttribute("userRole", AccountRole.UNKNOWN);
        }
        return REDIRECT_KEYWORD + AccountRole.UNKNOWN.getRedirectPath();
    }
}
