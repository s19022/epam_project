package com.example.InspectionBoard.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String key = request.getParameter("key");
        if (key == null){
            ((HttpServletResponse) response).sendError(403, "not an admin");
            return;
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
