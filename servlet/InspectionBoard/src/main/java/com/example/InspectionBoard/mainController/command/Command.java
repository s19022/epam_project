package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.model.enums.RequestType;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    String execute(HttpServletRequest request, RequestType requestTypes);
}
