package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.exceptions.AlreadyRegisteredException;
import com.example.InspectionBoard.exceptions.CannotRegisterToFacultyException;
import com.example.InspectionBoard.exceptions.NoSuchAccountException;
import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.FacultyRegistrationStatus;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.FacultyRegistrationService;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.LOGIN;
import static com.example.InspectionBoard.model.enums.FacultyRegistrationStatus.*;

public class RegisterToFacultyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String enrolleeLogin = (String) request.getSession().getAttribute(LOGIN);
        String facultyName = request.getParameter("facultyName");
        try {
            new FacultyRegistrationService().register(enrolleeLogin, facultyName);
            setFacultyRegistrationStatus(request, SUCCESSFULLY);
        } catch (NoSuchAccountException e) {
            setFacultyRegistrationStatus(request, NO_SUCH_ACCOUNT);
        } catch (NoSuchFacultyException e) {
            setFacultyRegistrationStatus(request, NO_SUCH_FACULTY);
        } catch (CannotRegisterToFacultyException e) {
            setFacultyRegistrationStatus(request, CANNOT_REGISTER);
        } catch (AlreadyRegisteredException e) {
            setFacultyRegistrationStatus(request, ALREADY_REGISTERED);
        }
        return "/WEB-INF/faculty/info.jsp";
    }

    private static void setFacultyRegistrationStatus(HttpServletRequest request, FacultyRegistrationStatus status){
        request.setAttribute("facultyRegistrationStatus", status);
    }
}
