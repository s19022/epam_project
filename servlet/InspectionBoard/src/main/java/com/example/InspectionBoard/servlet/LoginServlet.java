package com.example.InspectionBoard.servlet;

import com.example.InspectionBoard.entity.Account;
import com.example.InspectionBoard.repository.AccountRepository;
import exceptions.WrongLoginPasswordException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.HashSet;

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    public static final String LOGGED_USERS = "loggedUsers";
    @Override
    public void init(ServletConfig config){
        config.getServletContext()
                .setAttribute(LOGGED_USERS, new HashSet<Integer>());

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        if (! isValid(login) && isValid(password)){
            response.sendError(400, "login/pass wrong(null)");
            return;
        }
        try{
            Account account = AccountRepository.getInstance().getAccount(login, password);
            HttpSession session = request.getSession();
            session.setAttribute("id", account.getId());
            session.setAttribute("userRole", account.getRole().name());
            addLoggedUser(request.getServletContext(), account.getId());
        }catch (WrongLoginPasswordException ex){
            response.sendError(400, "login/pass wrong(not in db)");
            return;
        }
        response.sendRedirect("subject");
        //TODO to main page redirect
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private boolean isValid(String toCheck){
        return !(toCheck == null || toCheck.trim().isEmpty());
    }

    private void addLoggedUser(ServletContext context, int userId){
        HashSet<Integer> loggedUsers = (HashSet<Integer>) context.getAttribute(LOGGED_USERS);
        if (isLoggedIn(loggedUsers, userId)){
            //todo throw exception given user already logged in
            return;
        }
        loggedUsers.add(userId);
        context.setAttribute(LOGGED_USERS, loggedUsers);
    }

    private boolean isLoggedIn(HashSet<Integer> loggedUsers, int userId){
        return loggedUsers.stream().anyMatch(i -> i == userId);
    }
}
