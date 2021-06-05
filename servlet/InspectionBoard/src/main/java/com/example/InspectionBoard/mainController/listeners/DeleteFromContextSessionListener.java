package com.example.InspectionBoard.mainController.listeners;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;
import java.util.HashSet;

import static com.example.InspectionBoard.Constants.LOGGED_USERS;
import static com.example.InspectionBoard.Constants.LOGIN;

public class DeleteFromContextSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("created");
        long creationTime = se.getSession().getCreationTime();
        Date d = new Date(creationTime);
        System.out.println(d);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void sessionDestroyed(HttpSessionEvent se) {
        ServletContext context = se.getSession().getServletContext();
        String login  = (String) se.getSession().getAttribute(LOGIN);
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute(LOGGED_USERS);
        loggedUsers.remove(login);
        context.setAttribute(LOGGED_USERS, loggedUsers);
        se.getSession().setAttribute("userRole", AccountRole.UNKNOWN);
    }
}
