package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.exceptions.AccountIsBlockedException;
import com.example.InspectionBoard.exceptions.ParsingException;
import com.example.InspectionBoard.model.entity.Account;
import com.example.InspectionBoard.model.entity.Subject;
import com.example.InspectionBoard.model.dao.implementation.JDBCAccountDao;
import com.example.InspectionBoard.exceptions.UserAlreadyLoggedInException;
import com.example.InspectionBoard.exceptions.WrongLoginPasswordException;
import com.example.InspectionBoard.model.dao.implementation.JDBCSubjectDao;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

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
        return REDIRECT_KEYWORD + "/login.jsp";
    }

    private String executePost(HttpServletRequest request){
        try{
            Account account = getAccount(request);
            addAccountToSession(request.getSession(), account);
            addAccountToContext(request.getServletContext(), account);
            List<Subject> subjects = JDBCSubjectDao.getInstance().getSubjects();
            request.getSession().setAttribute("subject", subjects);
            return REDIRECT_KEYWORD + account.getRole().getRedirectPath();
        }catch (WrongLoginPasswordException ex){
            return "/WEB-INF/error/400.jsp";
        }catch (AccountIsBlockedException ex){
            return "/WEB-INF/error/accountBlocked.jsp";
        } catch (UserAlreadyLoggedInException ex){
            return "/WEB-INF/error/exists.jsp";
        }catch (ParsingException ex){
            return "/WEB-INF/error/404.jsp";
        }
    }

    private Account getAccount(HttpServletRequest request)
            throws WrongLoginPasswordException, AccountIsBlockedException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        return JDBCAccountDao.getInstance().getAccount(login, password);
    }

    private void addAccountToSession(HttpSession session, Account account){
        session.setAttribute("id", account.getId());
        session.setAttribute(USER_ROLE, account.getRole().name());
    }

    private void addAccountToContext(ServletContext context, Account account) throws UserAlreadyLoggedInException {
        HashSet<Integer> loggedUsers = (HashSet<Integer>) context.getAttribute(LOGGED_USERS);
        if (isLoggedIn(loggedUsers, account.getId())){
            throw new UserAlreadyLoggedInException();
        }
        loggedUsers.add(account.getId());
        context.setAttribute(LOGGED_USERS, loggedUsers);
    }
}
