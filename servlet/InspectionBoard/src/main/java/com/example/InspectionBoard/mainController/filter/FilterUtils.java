package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static com.example.InspectionBoard.Constants.*;

public class FilterUtils {

    public static boolean isValid(String toCheck){
        if (toCheck == null){
            return false;
        }
        return !toCheck.trim().isEmpty();
    }

    public static AccountRole getAccountRole(HttpServletRequest request){
        HttpSession session = request.getSession();
        String login  = (String)session.getAttribute(LOGIN);
        if (!isLoggedIn(request, login)){
            return AccountRole.UNKNOWN;
        }
        Object userRole = request.getSession().getAttribute(USER_ROLE);
        return parseAccountRole(userRole);
    }

    @SuppressWarnings("unchecked")
    private static boolean isLoggedIn(HttpServletRequest request, String login){
        Object loggedUsers = request.getServletContext().getAttribute(LOGGED_USERS);
        if (loggedUsers == null){
            return false;
        }
        return ((HashSet<String>) loggedUsers).contains(login);
    }

    private static AccountRole parseAccountRole(Object attribute) {
        try {
            if (attribute instanceof AccountRole) {
                return (AccountRole) attribute;
            }
            if (attribute instanceof String) {
                return AccountRole.valueOf((String) attribute);
            }
        } catch (RuntimeException ignore) {}
        return AccountRole.UNKNOWN;
    }
}
