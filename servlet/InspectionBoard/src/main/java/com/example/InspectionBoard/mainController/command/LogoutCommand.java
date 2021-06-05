package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.mainController.Utils;
import com.example.InspectionBoard.model.enums.AccountRole;
import com.example.InspectionBoard.model.enums.RequestType;

import javax.servlet.http.HttpServletRequest;

import static com.example.InspectionBoard.Constants.*;

public class LogoutCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType requestType) {
        Utils.removeFromContext(request.getSession());
        return REDIRECT_KEYWORD + AccountRole.UNKNOWN.getRedirectPath();
    }
}
