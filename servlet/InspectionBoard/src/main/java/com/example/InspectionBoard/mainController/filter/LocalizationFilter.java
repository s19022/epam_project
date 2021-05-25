package com.example.InspectionBoard.mainController.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class LocalizationFilter implements Filter {
    private static final Locale DEFAULT = Locale.ENGLISH;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Map<String, String[]> m = request.getParameterMap();
        for (Map.Entry<String, String[]> item : m.entrySet()){
            System.out.println(item.getKey() + ", " + item.getValue()[0]);
        }
        String lang = request.getParameter("lang");
        String locale = (String) request.getSession().getAttribute("locale");
        if (lang == null){
            if (locale == null){
                setLocale(request, response, DEFAULT.getLanguage());
            }
        }else {
            setLocale(request, response, lang);
        }
        System.out.println(request.getSession().getAttribute("locale"));
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void destroy() {
    }

    private void setLocale(HttpServletRequest request, HttpServletResponse response, String locale){
        request.getSession().setAttribute("locale", locale);
        response.setLocale(new Locale(locale));
    }
}
