package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.model.entity.Account;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.AccountService;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.model.enums.LoginStatus.ACCOUNT_IS_BLOCKED;
import static com.example.InspectionBoard.model.enums.LoginStatus.LOGIN_PASS_WRONG;

public class LoginCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType requestType) {
        switch (requestType){
            case POST:
                return executePost(request);
            case GET:
            default:
                return "/WEB-INF/login.jsp";
        }
    }

    private String executePost(HttpServletRequest request){
        try{
            Account account = getAccount(request);
            addAccountToSession(request.getSession(), account);
            addAccountToContext(request.getServletContext(), account);
            return REDIRECT_KEYWORD + account.getRole().getRedirectPath();
        } catch (WrongLoginPasswordException ex){
            request.setAttribute(LOGIN_STATUS, LOGIN_PASS_WRONG);
        } catch (AccountIsBlockedException ex){
            request.setAttribute(LOGIN_STATUS, ACCOUNT_IS_BLOCKED);
        }
        return "/WEB-INF/login.jsp";
    }

    private Account getAccount(HttpServletRequest request)
            throws WrongLoginPasswordException, AccountIsBlockedException {
        String login = (String)request.getAttribute(LOGIN);
        String password = (String)request.getAttribute(PASSWORD);
        return new AccountService().getAccount(login, password);
    }

    private void addAccountToSession(HttpSession session, Account account){
        session.setAttribute(LOGIN, account.getLogin());
        session.setAttribute(USER_ROLE, account.getRole().name());
    }

    @SuppressWarnings("unchecked")
    private void addAccountToContext(ServletContext context, Account account){
        HashSet<String> loggedUsers = (HashSet<String>) context.getAttribute(LOGGED_USERS);
        loggedUsers.add(account.getLogin());
        context.setAttribute(LOGGED_USERS, loggedUsers);
    }
}
