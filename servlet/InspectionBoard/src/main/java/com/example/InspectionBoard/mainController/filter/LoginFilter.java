package com.example.InspectionBoard.mainController.filter;

import com.example.InspectionBoard.model.enums.LoginStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;

import static com.example.InspectionBoard.Constants.*;

public class LoginFilter implements Filter {
    private HttpServletRequest request;
    private HttpServletResponse response;
    private String login, password;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        request = (HttpServletRequest) servletRequest;
        response = (HttpServletResponse) servletResponse;

        if ("POST".equalsIgnoreCase(((HttpServletRequest) servletRequest).getMethod())){
            processRequest(filterChain);
        }else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }


    @Override
    public void init(FilterConfig filterConfig){
    }

    @Override
    public void destroy() {
    }

    private void processRequest(FilterChain filterChain) throws IOException, ServletException {
        login = request.getParameter(LOGIN);
        password = request.getParameter(PASSWORD);

       if(!validate()){
           return;
       }
       login = decode(login);
       password = decode(password);

        if (isLoggedIn(login)){
            setLoginStatus(LoginStatus.ALREADY_LOGGED_IN);
            forwardRequest();
            return;
        }
        request.setAttribute(LOGIN, login);
        filterChain.doFilter(request, response);
    }

    private void forwardRequest() throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
    }

    @SuppressWarnings("unchecked")
    private boolean isLoggedIn(String login){
        HashSet<String> loggedUsers = (HashSet<String>)
                request.getServletContext().getAttribute(LOGGED_USERS);
        return loggedUsers.stream().anyMatch(login::equals);
    }

    private void setLoginStatus(LoginStatus status){
        request.setAttribute(LOGIN_STATUS, status);
    }

    private boolean validate() throws ServletException, IOException {
        if (!(isValid(login))){
            setLoginStatus(LoginStatus.LOGIN_EMPTY);
            forwardRequest();
            return false;
        }
        if (!isValid(password)){
            setLoginStatus(LoginStatus.PASS_EMPTY);
            forwardRequest();
            return false;
        }
        return true;
    }

    private static boolean isValid(String toCheck){
        if (toCheck == null){
            return false;
        }
        String decoded = decode(toCheck);
        return !decoded.trim().isEmpty();
    }

    private static String decode(String toDecode){
        byte[] decoded = Base64.getDecoder().decode(toDecode.getBytes(StandardCharsets.UTF_8));
        return new String(decoded);
    }
}