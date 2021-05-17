package com.example.InspectionBoard.command;

import com.example.InspectionBoard.entity.Account;
import com.example.InspectionBoard.entity.AccountRole;
import exceptions.WrongLoginPasswordException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.spi.http.HttpContext;
import java.util.HashSet;

import static com.example.InspectionBoard.command.CommandUtility.isLoggedIn;
import static com.example.InspectionBoard.servlet.LoginServlet.LOGGED_USERS;


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
