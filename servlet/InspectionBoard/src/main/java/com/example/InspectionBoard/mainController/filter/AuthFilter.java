package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static com.example.InspectionBoard.Constants.*;

public class AuthFilter implements Filter {
    private final List<String> allCanAccess = new ArrayList<>();
    private final List<String> unknownRoleCanAccess = new ArrayList<>();
    private final List<String> loggedInCanAccess = new ArrayList<>();
    private final List<String> enrolleeRoleCanAccess = new ArrayList<>();
    private final List<String> adminRoleCanAccess = new ArrayList<>();
    private final List<String> supportedUrlList = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig){
        allCanAccess.add("faculties");
        allCanAccess.add("faculties/info");

        unknownRoleCanAccess.addAll(allCanAccess);
        unknownRoleCanAccess.add("");
        unknownRoleCanAccess.add("index.jsp");
        unknownRoleCanAccess.add("login");
        unknownRoleCanAccess.add("register.jsp");
        unknownRoleCanAccess.add("register");
        //fixme

        loggedInCanAccess.add("logout");

        enrolleeRoleCanAccess.addAll(allCanAccess);
        enrolleeRoleCanAccess.addAll(loggedInCanAccess);
        enrolleeRoleCanAccess.add("enrollee/main");
        enrolleeRoleCanAccess.add("faculties/register");

        adminRoleCanAccess.addAll(allCanAccess);
        adminRoleCanAccess.addAll(loggedInCanAccess);
        adminRoleCanAccess.add("admin/main");
        adminRoleCanAccess.add("admin/enrollee");
        adminRoleCanAccess.add("admin/enrollee/block");
        adminRoleCanAccess.add("admin/enrollee/unblock");
        adminRoleCanAccess.add("faculties/delete");

        supportedUrlList.addAll(allCanAccess);
        supportedUrlList.addAll(loggedInCanAccess);
        supportedUrlList.addAll(unknownRoleCanAccess);
        supportedUrlList.addAll(enrolleeRoleCanAccess);
        supportedUrlList.addAll(adminRoleCanAccess);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        AccountRole role = getAccountRole(request);
        setDefaultRole(request, role);

        String path = request.getRequestURI();
        path = path.replaceAll(".*/" + APP_NAME +"/" , "");
        if (!isSupported(path)){
            response.sendError(404);
            return;
        }
        if (canAccess(role, path)) {
            filterChain.doFilter(request, response);
            return;
        }
        response.sendError(403);
    }

    @Override
    public void destroy() {
    }

    private boolean canAccess(AccountRole role, String path){
        switch (role){
            case ENROLLEE:
                return enrolleeRoleCanAccess.contains(path);
            case ADMIN:
                return adminRoleCanAccess.contains(path);
            case UNKNOWN:
                return unknownRoleCanAccess.contains(path);
        }
        return false;
    }

    public static AccountRole getAccountRole(HttpServletRequest request){
        HttpSession session = request.getSession();
        String login  = (String)session.getAttribute("login");
        if (!isLoggedIn(request, login)){
            return AccountRole.UNKNOWN;
        }
        Object userRole = request.getSession().getAttribute(USER_ROLE);
        return parseAccountRole(userRole);
    }

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

    private boolean isSupported(String path){
        return supportedUrlList.contains(path);
    }

    private static void setDefaultRole(HttpServletRequest request, AccountRole actual){
        if (actual != AccountRole.UNKNOWN){
            //getAccount method returns UNKNOWN role in case USER_ROLE attribute
            // is null or can't be parsed to AccountRole
            return;
        }
        request.getSession().setAttribute(USER_ROLE, AccountRole.UNKNOWN);
    }
}
