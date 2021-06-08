package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.mainController.filter.FilterUtils.getAccountRole;

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
        unknownRoleCanAccess.add("register");

        loggedInCanAccess.add("logout");

        enrolleeRoleCanAccess.addAll(allCanAccess);
        enrolleeRoleCanAccess.addAll(loggedInCanAccess);
        enrolleeRoleCanAccess.add("enrollee/main");
        enrolleeRoleCanAccess.add("faculties/register");
        enrolleeRoleCanAccess.add("enrollee/createSubject");

        adminRoleCanAccess.addAll(allCanAccess);
        adminRoleCanAccess.addAll(loggedInCanAccess);
        adminRoleCanAccess.add("admin/main");
        adminRoleCanAccess.add("admin/enrollee");
        adminRoleCanAccess.add("admin/enrollee/block");
        adminRoleCanAccess.add("admin/enrollee/unblock");
        adminRoleCanAccess.add("faculties/delete");
        adminRoleCanAccess.add("faculties/changeRegistrationStatus");
        adminRoleCanAccess.add("faculties/create");
        adminRoleCanAccess.add("faculties/modify");
        adminRoleCanAccess.add("faculties/createSubject");

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
