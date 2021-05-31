package com.example.InspectionBoard.mainController.filter.admin;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.mainController.filter.FilterUtils.getAccountRole;
import static com.example.InspectionBoard.model.enums.AccountRole.ADMIN;

public class AdminAccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse) res;
        AccountRole role = getAccountRole(request.getSession().getAttribute(USER_ROLE));
        if (role == ADMIN){
            filterChain.doFilter(req, res);
            return;
        }
        response.sendError(403);
    }

    @Override
    public void destroy() {
    }
}
