package com.example.InspectionBoard.mainController.command.admin;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.dto.db.FindByPageDto;
import com.example.InspectionBoard.model.entity.Enrollee;
import com.example.InspectionBoard.model.service.EnrolleeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.InspectionBoard.Constants.*;

public class AdminEnrolleeCommand implements Command {
    private static final int DEFAULT_ITEMS_PER_PAGE = 5;

    @Override
    public String execute(HttpServletRequest request, RequestType requestTypes) {
        EnrolleeService service = new EnrolleeService();

        int itemsPerPage = getItemsPerPage(request.getParameter(ITEMS_PER_PAGE));
        int numberOfPages = service.getNumberOfPages(itemsPerPage);
        int pageNumber = getPage(request.getParameter(PAGE_NUMBER));
        if (pageNumber > numberOfPages){
            pageNumber = 1;
        }

        FindByPageDto page = new FindByPageDto(pageNumber, itemsPerPage);
        List<Enrollee> list = service.findAllByPage(page);
        setAttributes(request, itemsPerPage, numberOfPages, pageNumber, list);
        return "/WEB-INF/admin/enrolleeInfo.jsp";
    }

    private static void setAttributes(HttpServletRequest request, int itemsPerPage, int numberOfPages,
                                      int currentPageNumber, List<Enrollee> enrolleeList){
        request.setAttribute(ITEMS_PER_PAGE, itemsPerPage);
        request.setAttribute(NUMBER_OF_PAGES, numberOfPages);
        request.setAttribute(PAGE_NUMBER, currentPageNumber);
        request.setAttribute(ENROLLEE_LIST, enrolleeList);
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
