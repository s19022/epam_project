package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.dto.ChangeFacultyRegistrationStatusDto;
import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.model.enums.RegistrationStatus;
import com.example.InspectionBoard.model.enums.RequestType;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;

public class ChangeFacultyRegistrationStatusCommand implements Command {

    public static final String ENROLLEE_LOGIN = "enrolleeLogin";
    public static final String FACULTY_NAME = "facultyName";
    public static final String NEW_STATUS = "newStatus";

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String enrolleeLogin = request.getParameter(ENROLLEE_LOGIN);
        String facultyName = request.getParameter(FACULTY_NAME);
        String newStatus = request.getParameter(NEW_STATUS);
        ChangeFacultyRegistrationStatusDto dto = new ChangeFacultyRegistrationStatusDto(enrolleeLogin, facultyName, RegistrationStatus.valueOf(newStatus));
        return REDIRECT_KEYWORD + AccountRole.ADMIN.getRedirectPath();
    }
}
