package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.exceptions.ValidationException;
import com.example.InspectionBoard.model.dto.SaveEnrolleeDto;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.*;


public class RegisterCommand implements Command{
    private static final Logger LOGGER = LogManager.getLogger(RegisterCommand.class.getName());

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
        try{
            SaveEnrolleeDto enrollee = parseSaveEnrollee(request);
            new AccountService().createEnrollee(enrollee);
        }catch (ValidationException ex){
            LOGGER.warn(ex);
            return "/WEB-INF/error/400.jsp";
        }
        return REDIRECT_KEYWORD + "/login";
    }

    private SaveEnrolleeDto parseSaveEnrollee(HttpServletRequest request) throws ValidationException {
        String login = request.getParameter("login");
        String password = request.getParameter("pass");
        String firstName = request.getParameter("firstName");
        String fatherName = request.getParameter("fatherName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        String region = request.getParameter("region");
        String schoolName = request.getParameter("schoolName");
        byte[] certificateScan = {};
        return SaveEnrolleeDto.getInstance(login, password, firstName, fatherName,
                lastName, email, city, region, schoolName, certificateScan);
    }

    private String executeGet(){
        return "/WEB-INF/register.jsp";
    }
}
