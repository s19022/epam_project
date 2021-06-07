package com.example.InspectionBoard.mainController.command.enrollee;

import com.example.InspectionBoard.exceptions.NotUniqueSubjectException;
import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.dto.CreateEnrolleeSubjectDto;
import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.EnrolleeSubjectService;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.model.enums.CreateEnrolleeSubjectResult.NOT_UNIQUE_SUBJECT;
import static com.example.InspectionBoard.model.enums.CreateEnrolleeSubjectResult.SUCCESS;

public class CreateEnrolleeSubjectCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String subjectName = (String) request.getAttribute(SUBJECT_NAME);
        String enrolleeLogin = (String) request.getSession().getAttribute(LOGIN);
        int mark = (int) request.getAttribute(MARK);
        System.out.println(subjectName);
        System.out.println(enrolleeLogin);
        System.out.println(mark);
        CreateEnrolleeSubjectDto dto = new CreateEnrolleeSubjectDto(subjectName, enrolleeLogin, mark);
        try{
            new EnrolleeSubjectService().create(dto);
            request.getSession().setAttribute(CREATE_ENROLLEE_SUBJECT_RESULT, SUCCESS);
        } catch (NotUniqueSubjectException e) {
            request.getSession().setAttribute(CREATE_ENROLLEE_SUBJECT_RESULT, NOT_UNIQUE_SUBJECT);
        }
        return REDIRECT_KEYWORD + AccountRole.ENROLLEE.getRedirectPath();
    }
}
