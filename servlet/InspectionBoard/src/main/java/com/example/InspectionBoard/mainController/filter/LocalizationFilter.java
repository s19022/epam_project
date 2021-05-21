package com.example.InspectionBoard.mainController.filter;

import javax.servlet.*;
import java.io.IOException;

public class LocalizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        String lang = request.getParameter("lang");
//        Locale locale = new Locale(lang);
//        Locale.setDefault(locale);
//        System.out.println(locale);
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void destroy() {
    }
}
