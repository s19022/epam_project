package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.exceptions.MarkIsNotValidException;
import com.example.InspectionBoard.exceptions.NoSuchSubjectException;
import com.example.InspectionBoard.exceptions.NotUniqueSubjectException;
import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.dto.CreateFacultySubjectDto;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.service.RequiredSubjectService;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.model.enums.CreateEnrolleeSubjectResult.*;
import static com.example.InspectionBoard.model.enums.CreateEnrolleeSubjectResult.MARK_IS_INVALID;

public class CreateNewFacultySubjectCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        String subjectName = (String) request.getAttribute(SUBJECT_NAME);
        String facultyName = (String) request.getSession().getAttribute(FACULTY_NAME);
        int mark = (int) request.getAttribute(MARK);

        CreateFacultySubjectDto dto = new CreateFacultySubjectDto(subjectName, facultyName, mark);
        try{
            new RequiredSubjectService().create(dto);
            request.getSession().setAttribute(CREATE_ENROLLEE_SUBJECT_RESULT, SUCCESS);
        } catch (NotUniqueSubjectException e) {
            request.getSession().setAttribute(CREATE_ENROLLEE_SUBJECT_RESULT, NOT_UNIQUE_SUBJECT);
        } catch (NoSuchSubjectException e) {
            request.getSession().setAttribute(CREATE_ENROLLEE_SUBJECT_RESULT, NO_SUCH_SUBJECT);
        } catch (MarkIsNotValidException e) {
            request.getSession().setAttribute(CREATE_ENROLLEE_SUBJECT_RESULT, MARK_IS_INVALID);
        }
        return REDIRECT_KEYWORD + "/faculties/info";
    }
}
