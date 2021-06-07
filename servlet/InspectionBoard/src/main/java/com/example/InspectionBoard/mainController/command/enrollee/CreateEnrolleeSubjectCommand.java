package com.example.InspectionBoard.mainController.command.enrollee;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.model.enums.RequestType;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;

public class CreateEnrolleeSubjectCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        return REDIRECT_KEYWORD + AccountRole.ENROLLEE.getRedirectPath();
    }
}
