package com.example.InspectionBoard.mainController.command.admin;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.dto.db.FindByPageDto;
import com.example.InspectionBoard.model.entity.Enrollee;
import com.example.InspectionBoard.model.service.EnrolleeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class AdminEnrolleeCommand implements Command {
    private static final int DEFAULT_ITEMS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        int pageNumber = getPage(request.getParameter("pageNumber"));
        int itemsPerPage = getItemsPerPage(request.getParameter("itemsPerPage"));
        FindByPageDto page = new FindByPageDto(pageNumber, itemsPerPage);
        List<Enrollee> list = new EnrolleeService().findAllByPage(page);
        request.setAttribute("enrollee", list);
        return "/WEB-INF/admin/enrolleeInfo.jsp";
    }

    private static int getPage(String pageNumber){
        try{
            return Integer.parseInt(pageNumber);
        }catch (NumberFormatException ex){
            return 1;
        }
    }

    private static int getItemsPerPage(String itemPerPage){
        try{
            return Integer.parseInt(itemPerPage);
        }catch (NumberFormatException ex){
            return DEFAULT_ITEMS_PER_PAGE;
        }
    }
}
