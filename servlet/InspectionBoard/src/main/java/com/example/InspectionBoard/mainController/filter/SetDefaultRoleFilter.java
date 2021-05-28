package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.InspectionBoard.Constants.USER_ROLE;

public class SetDefaultRoleFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        Object userRole = session.getAttribute(USER_ROLE);
        if (userRole == null){
            session.setAttribute(USER_ROLE, AccountRole.UNKNOWN);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void destroy() {
    }
}
