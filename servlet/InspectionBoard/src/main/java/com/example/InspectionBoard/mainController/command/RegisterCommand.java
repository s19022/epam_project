package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.exceptions.ValidationException;
import com.example.InspectionBoard.model.dto.SaveEnrolleeDto;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

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
                return "/WEB-INF/register.jsp";
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
        String login = request.getParameter(LOGIN);
        String password = request.getParameter(PASSWORD);
        String firstName = request.getParameter(FIRST_NAME);
        String fatherName = request.getParameter(FATHER_NAME);
        String lastName = request.getParameter(LAST_NAME);
        String email = request.getParameter(EMAIL);
        String city = request.getParameter(CITY);
        String region = request.getParameter(REGION);
        String schoolName = request.getParameter(SCHOOL_NAME);
        byte[] certificateScan = getCertificateScan(request);
        return SaveEnrolleeDto.getInstance(login, password, firstName, fatherName,
                lastName, email, city, region, schoolName, certificateScan);
    }

    private byte[] getCertificateScan(HttpServletRequest request) {
        try{
            Optional<Part> first = request.getParts().stream().filter(p -> MARKS_SCAN.equals(p.getName())).findFirst();
            if (!first.isPresent()){
                return new byte[]{};
            }
            InputStream is = first.get().getInputStream();
            byte[] scan = new byte[is.available()];
            is.read(scan);
            return scan;
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
        return new byte[]{};
    }
}
