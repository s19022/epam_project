package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.mainController.filter.FilterUtils.getAccountRole;


public class LoggedInFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        AccountRole role = getAccountRole(session.getAttribute(USER_ROLE));
        if (role == AccountRole.ENROLLEE || role == AccountRole.ADMIN) {
            filterChain.doFilter(request, response);
            return;
        }
        ((HttpServletResponse) response).sendError(403);
    }
}
