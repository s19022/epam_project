package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.InspectionBoard.Constants.*;


public class FacultyCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, RequestType type) {
        String facultyOrder = request.getParameter(FACULTY_ORDER);
        setAttributeOrderedBy(request, facultyOrder);
        setFaculties(request, getFacultyList(facultyOrder));
        return "/WEB-INF/faculty/main.jsp";
    }

    private static void setAttributeOrderedBy(HttpServletRequest request, String orderedBy){
        request.getSession().setAttribute(FACULTY_ORDER, orderedBy);
    }

    private static void setFaculties(HttpServletRequest request, List<Faculty> faculties){
        request.getSession().setAttribute(FACULTIES, faculties);
    }

    private static List<Faculty> getFacultyList(String orderBy){
        FacultyService service = new FacultyService();
        if (orderBy == null){
            return service.findAllOrderByNameAsc();
        }
        switch (orderBy){
            case NAME_DESC:
                return service.findAllOrderByNameDesc();
            case ALL_PLACES_DESC:
                return service.findAllOrderByAllPlacesDesc();
            case BUDGET_PLACES_DESC:
                return service.findAllOrderByBudgetPlacesDesc();
            case NAME_ASC:
            default:
                return service.findAllOrderByNameAsc();
        }
    }
}
