package com.example.InspectionBoard.mainController.filter;



import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashSet;

import static com.example.InspectionBoard.Constants.LOGGED_USERS;
import static com.example.InspectionBoard.Constants.LOGIN_STATUS;

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

        if (!(isValid(login))){
            setLoginStatus("Login is empty or blank");
            forwardRequest();
            return;
        }
        if (!isValid(password)){
            setLoginStatus("Password is empty or blank");
            forwardRequest();
            return;
        }
        login = decode(login);
        if (isLoggedIn(login)){
            setLoginStatus("Already logged in");
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

    private void setLoginStatus(String message){
        request.setAttribute(LOGIN_STATUS, message);
    }

    private static boolean isValid(String toCheck){
        return !(toCheck == null || toCheck.trim().isEmpty());
    }

    public static String decode(String toDecode){
        byte[] decoded = Base64.getDecoder().decode(toDecode.getBytes(StandardCharsets.UTF_8));
        return new String(decoded);
    }
}
