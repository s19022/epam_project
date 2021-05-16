package com.example.InspectionBoard.servlet;

import com.example.InspectionBoard.entity.Account;
import com.example.InspectionBoard.repository.AccountRepository;
import exceptions.UserAlreadyLoggedInException;
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
        config.getServletContext().setAttribute(LOGGED_USERS, new HashSet<Integer>());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String redirectPath = "";
        try{
            Account account = getAccount(request);
            session.setAttribute("id", account.getId());
            session.setAttribute("userRole", account.getRole().name());
            addLoggedUser(request.getServletContext(), account.getId());
            redirectPath = account.getRole().getRedirectPath();
        }catch (WrongLoginPasswordException ex){
            redirectPath = "/WEB-INF/error/400.jsp";
        }catch (UserAlreadyLoggedInException ex){
            redirectPath = "/WEB-INF/error/exists.jsp";
        }
        request.getRequestDispatcher(redirectPath).forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    private void addLoggedUser(ServletContext context, int userId) throws UserAlreadyLoggedInException {
        HashSet<Integer> loggedUsers = (HashSet<Integer>) context.getAttribute(LOGGED_USERS);
        if (isLoggedIn(loggedUsers, userId)){
            throw new UserAlreadyLoggedInException();
        }
        loggedUsers.add(userId);
        context.setAttribute(LOGGED_USERS, loggedUsers);
    }

    private boolean isLoggedIn(HashSet<Integer> loggedUsers, int userId){
        return loggedUsers.stream().anyMatch(i -> i == userId);
    }

    private Account getAccount(HttpServletRequest request) throws WrongLoginPasswordException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        return AccountRepository.getInstance().getAccount(login, password);
    }
}
