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
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        login = decode(login);
        password = decode(password);

        if (!(isValid(login))){
            setLoginStatus(LoginStatus.LOGIN_EMPTY);
            forwardRequest();
            return;
        }
        if (!isValid(password)){
            setLoginStatus(LoginStatus.PASS_EMPTY);
            forwardRequest();
            return;
        }
        if (isLoggedIn(login)){
            setLoginStatus(LoginStatus.ALREADY_LOGGED_IN);
            forwardRequest();
            return;
        }
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

    private static boolean isValid(String toCheck){
        return !(toCheck == null || toCheck.trim().isEmpty());
    }

    public static String decode(String toDecode){
        byte[] decoded = Base64.getDecoder().decode(toDecode.getBytes(StandardCharsets.UTF_8));
        return new String(decoded);
    }
}