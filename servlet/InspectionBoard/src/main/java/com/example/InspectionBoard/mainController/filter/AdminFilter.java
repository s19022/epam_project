package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.model.enums.AccountRole;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.InspectionBoard.mainController.MainServlet.USER_ROLE;
import static com.example.InspectionBoard.model.enums.AccountRole.ADMIN;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
        //todo check if admin
        HttpServletRequest request = (HttpServletRequest)req;
        Object role = request.getSession().getAttribute(USER_ROLE);
        if (role instanceof String){
            if (AccountRole.valueOf((String) role) == ADMIN){
                filterChain.doFilter(req, res);
                return;
            }
        }
        ((HttpServletResponse) res).sendError(403);
    }

    @Override
    public void destroy() {
    }
}
