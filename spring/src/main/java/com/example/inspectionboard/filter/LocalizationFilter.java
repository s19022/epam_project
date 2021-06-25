package com.example.inspectionboard.filter;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

@Component
@Order(1)
public class LocalizationFilter implements Filter {
    private static final Locale DEFAULT = Locale.ENGLISH;
    public static final String LANG = "lang";
    public static final String LOCALE = "locale";

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
