package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.mainController.command.admin.AdminEnrolleeCommand;
import com.example.InspectionBoard.mainController.command.admin.AdminMainCommand;
import com.example.InspectionBoard.mainController.command.admin.BlockEnrolleeCommand;
import com.example.InspectionBoard.mainController.command.admin.UnblockEnrolleeCommand;
import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.InspectionBoard.Constants.APP_NAME;
import static com.example.InspectionBoard.Constants.USER_ROLE;
import static com.example.InspectionBoard.mainController.filter.FilterUtils.getAccountRole;

public class AuthFilter implements Filter {
    private final List<String> allCanAccess = new ArrayList<>();
    private final List<String> unknownRoleCanAccess = new ArrayList<>();
    private final List<String> loggedInCanAccess = new ArrayList<>();
    private final List<String> enrolleeRoleCanAccess = new ArrayList<>();
    private final List<String> adminRoleCanAccess = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig){
        allCanAccess.add("faculties");
        allCanAccess.add("faculties/info");

        unknownRoleCanAccess.addAll(allCanAccess);
        unknownRoleCanAccess.add("");
        unknownRoleCanAccess.add("index.jsp");
        unknownRoleCanAccess.add("login");
        unknownRoleCanAccess.add("register.jsp");
        //fixme

        loggedInCanAccess.add("logout");

        enrolleeRoleCanAccess.addAll(allCanAccess);
        enrolleeRoleCanAccess.addAll(loggedInCanAccess);
        enrolleeRoleCanAccess.add("enrollee/main");
        enrolleeRoleCanAccess.add("faculties/register");

        adminRoleCanAccess.addAll(allCanAccess);
        adminRoleCanAccess.addAll(loggedInCanAccess);
        adminRoleCanAccess.add("admin/main");
        adminRoleCanAccess.add("admin/enrollees");
        adminRoleCanAccess.add("admin/enrollees/block");
        adminRoleCanAccess.add("admin/enrollees/unblock");
        adminRoleCanAccess.add("faculties/delete");

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        AccountRole role = getAccountRole(request.getSession().getAttribute(USER_ROLE));
        String path = request.getRequestURI();
        path = path.replaceAll(".*/" + APP_NAME +"/" , "");
        System.out.println(path);
        if (canAccess(role, path)){
            filterChain.doFilter(request, response);
        }else{
            response.sendError(403);
        }
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
}
