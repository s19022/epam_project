package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static com.example.InspectionBoard.Constants.*;


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
        System.out.println("in login filter");
        AccountRole role = getAccountRole(session.getAttribute(USER_ROLE));
        if (role == AccountRole.USER || role == AccountRole.ADMIN) {
            filterChain.doFilter(request, response);
            return;
        }
        ((HttpServletResponse) response).sendError(403);
    }

    private AccountRole getAccountRole(Object attribute) {
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
