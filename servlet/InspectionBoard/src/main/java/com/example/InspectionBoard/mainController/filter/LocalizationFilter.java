package com.example.InspectionBoard.mainController.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

import static com.example.InspectionBoard.Constants.*;

public class LocalizationFilter implements Filter {
    private static final Locale DEFAULT = Locale.ENGLISH;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String lang = request.getParameter(LANG);
        String locale = (String) request.getSession().getAttribute(LOCALE);

        if (lang == null){
            if (locale == null){
                setLocale(request, response, DEFAULT.getLanguage());
            }
        }else {
            setLocale(request, response, lang);
        }
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void destroy() {
    }

    private void setLocale(HttpServletRequest request, HttpServletResponse response, String locale){
        request.getSession().setAttribute(LOCALE, locale);
        response.setLocale(new Locale(locale));
    }
}
