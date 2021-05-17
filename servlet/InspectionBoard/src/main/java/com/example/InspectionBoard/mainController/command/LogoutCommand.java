package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.model.entity.AccountRole;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static com.example.InspectionBoard.mainController.command.CommandUtility.isLoggedIn;
import static com.example.InspectionBoard.mainController.LoginServlet.LOGGED_USERS;


public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request) {
        ServletContext context = request.getServletContext();
        HttpSession session = request.getSession();
        HashSet<Integer> loggedUsers = (HashSet<Integer>) context.getAttribute(LOGGED_USERS);
        Integer userId = (Integer) session.getAttribute("id");
        if (!isLoggedIn(loggedUsers, userId)){
            System.out.println("wasn't in the system");
            return "/index.jsp";    // already logged out
            //todo add filter to prevent logged out users from accessing
        }

        loggedUsers.remove(userId);
        context.setAttribute(LOGGED_USERS, loggedUsers);

        session.setAttribute("userRole", AccountRole.UNKNOWN);
        return AccountRole.UNKNOWN.getRedirectPath();
    }
}
