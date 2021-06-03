package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.CommandUtility;
import com.example.InspectionBoard.model.enums.RequestType;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class FacultyCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, RequestType type) {
        String facultyOrder = request.getParameter("facultyOrder");
        setAttributeOrderedBy(request, facultyOrder);
        setFaculties(request, getFacultyList(facultyOrder));
        return "/WEB-INF/faculty/main.jsp";
    }

    private static void setAttributeOrderedBy(HttpServletRequest request, String orderedBy){
        request.getSession().setAttribute("facultyOrder", orderedBy);
    }

    private static void setFaculties(HttpServletRequest request, List<Faculty> faculties){
        request.getSession().setAttribute("faculties", faculties);
    }

    private static List<Faculty> getFacultyList(String orderBy){
        FacultyService service = new FacultyService();
        if (orderBy == null){
            return service.findAllOrderByNameAsc();
        }
        switch (orderBy){
            case "nameDesc":
                return service.findAllOrderByNameDesc();
            case "allPlacesDesc":
                return service.findAllOrderByAllPlacesDesc();
            case "budgetPlacesDesc":
                return service.findAllOrderByBudgetPlacesDesc();
            case "nameAsc":
            default:
                return service.findAllOrderByNameAsc();
        }
    }
}
