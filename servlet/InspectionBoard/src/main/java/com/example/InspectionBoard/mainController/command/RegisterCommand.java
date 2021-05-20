package com.example.InspectionBoard.mainController.command;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.mainController.MainServlet.REDIRECT_KEYWORD;

public class RegisterCommand implements Command{
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

    private String executePost(HttpServletRequest request){
        //todo save user to db
        return REDIRECT_KEYWORD + "/login.jsp";
    }

    private String executeGet(){
        return REDIRECT_KEYWORD + "/register.jsp";
    }
}
