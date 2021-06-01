package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.model.entity.Account;
import com.example.InspectionBoard.exceptions.UserAlreadyLoggedInException;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.service.AccountService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.mainController.command.CommandUtility.isLoggedIn;

public class LoginCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType requestType) {
        switch (requestType){
            case POST:
                return executePost(request);
            case GET:
            default:
                return executeGet();
        }
    }

    private String executeGet(){
        return "/WEB-INF/login.jsp";
    }

    private String executePost(HttpServletRequest request){
        try{
            Account account = getAccount(request);
            addAccountToSession(request.getSession(), account);
            addAccountToContext(request.getServletContext(), account);
            return REDIRECT_KEYWORD + account.getRole().getRedirectPath();
        }catch (WrongLoginPasswordException | AccountIsBlockedException | UserAlreadyLoggedInException ex){
            request.setAttribute(LOGIN_STATUS, ex);
            return "/WEB-INF/login.jsp";
        }
    }

    private Account getAccount(HttpServletRequest request)
            throws WrongLoginPasswordException, AccountIsBlockedException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        return AccountService.getAccount(login, password);
    }

    private void addAccountToSession(HttpSession session, Account account){
        session.setAttribute("login", account.getLogin());
        session.setAttribute(USER_ROLE, account.getRole().name());
    }

    private void addAccountToContext(ServletContext context, Account account) throws UserAlreadyLoggedInException {
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute(LOGGED_USERS);
        if (isLoggedIn(loggedUsers, account.getLogin())){
            throw new UserAlreadyLoggedInException();
        }
        loggedUsers.add(account.getLogin());
        context.setAttribute(LOGGED_USERS, loggedUsers);
    }
}
