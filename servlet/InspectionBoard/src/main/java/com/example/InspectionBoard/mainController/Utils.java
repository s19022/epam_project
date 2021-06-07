package com.example.InspectionBoard.mainController;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static com.example.InspectionBoard.Constants.*;

public class Utils {

    @SuppressWarnings("unchecked")
    public static void removeFromContext(HttpSession session){
        ServletContext context = session.getServletContext();
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute(LOGGED_USERS);
        String login  = (String) session.getAttribute(LOGIN);
        if (isLoggedIn(loggedUsers, login)) {
            loggedUsers.remove(login);
            context.setAttribute(LOGGED_USERS, loggedUsers);
            session.setAttribute(USER_ROLE, AccountRole.UNKNOWN);
        }
    }

    public static boolean isLoggedIn(HashSet<String> loggedUsers, String login){
        if (login == null){
            return false;
        }
        return loggedUsers.stream().anyMatch(login::equals);
    }

    public static int getInt(String toParse){
        try{
            return Integer.parseInt(toParse);
        }catch (NumberFormatException ex){
            return 0;
        }
    }
}
