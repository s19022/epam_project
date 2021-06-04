package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.exceptions.NotEnoughPlacesException;
import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.dto.ChangeFacultyRegistrationStatusDto;
import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.model.enums.ChangeFacultyRegistrationResult;
import com.example.InspectionBoard.model.enums.RegistrationStatus;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.FacultyRegistrationService;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.FACULTY_NAME;
import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;

public class ChangeFacultyRegistrationStatusCommand implements Command {

    public static final String ENROLLEE_LOGIN = "enrolleeLogin";
    public static final String NEW_STATUS = "newStatus";
    public static final String CHANGE_FACULTY_REGISTRATION_RESULT = "changeFacultyRegistrationResult";

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String enrolleeLogin = request.getParameter(ENROLLEE_LOGIN);
        String facultyName = request.getParameter(FACULTY_NAME);
        String newStatus = request.getParameter(NEW_STATUS);
        ChangeFacultyRegistrationStatusDto dto = new ChangeFacultyRegistrationStatusDto(enrolleeLogin, facultyName, RegistrationStatus.valueOf(newStatus));
        ChangeFacultyRegistrationResult result = changeFacultyRegistration(dto);
        request.getSession().setAttribute(CHANGE_FACULTY_REGISTRATION_RESULT, result);
        return REDIRECT_KEYWORD + AccountRole.ADMIN.getRedirectPath();
    }

    private ChangeFacultyRegistrationResult changeFacultyRegistration(ChangeFacultyRegistrationStatusDto dto){
        try{
            new FacultyRegistrationService().changeStatus(dto);
        } catch (NotEnoughPlacesException e) {
            return ChangeFacultyRegistrationResult.NOT_ENOUGH_PLACES;
        } catch (NoSuchFacultyException e) {
            return ChangeFacultyRegistrationResult.NO_SUCH_FACULTY;
        }
        return ChangeFacultyRegistrationResult.SUCCESS;
    }
}
