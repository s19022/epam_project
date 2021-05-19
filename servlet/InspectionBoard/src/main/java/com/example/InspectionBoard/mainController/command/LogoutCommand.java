package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static com.example.InspectionBoard.mainController.MainServlet.REDIRECT_KEYWORD;
import static com.example.InspectionBoard.mainController.command.CommandUtility.isLoggedIn;
import static com.example.InspectionBoard.mainController.MainServlet.LOGGED_USERS;


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
            //todo add filter to prevent logged out users from accessing
        }
        return REDIRECT_KEYWORD + AccountRole.UNKNOWN.getRedirectPath();
    }
}
