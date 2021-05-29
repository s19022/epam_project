package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.CommandUtility;
import com.example.InspectionBoard.mainController.command.RequestType;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public class FacultyCommand implements Command {

    @Override
    public String execute(HttpServletRequest request, RequestType type) {
        CommandUtility.printParameters(request);
        String facultyOrder = request.getParameter("facultyOrder");
        setAttributeOrderedBy(request, facultyOrder);
        setFaculties(request, getFacultyList(facultyOrder));
        return "/faculty/main.jsp";
    }

    private static void setAttributeOrderedBy(HttpServletRequest request, String orderedBy){
        request.getSession().setAttribute("facultyOrder", orderedBy);
    }

    private static void setFaculties(HttpServletRequest request, List<Faculty> faculties){
        request.getSession().setAttribute("faculties", faculties);
    }

    private static List<Faculty> getFacultyList(String orderBy){
        if (orderBy == null){
            return FacultyService.findAllOrderByNameAsc();
        }
        switch (orderBy){
            case "nameDesc":
                return FacultyService.findAllOrderByNameDesc();
            case "allPlacesDesc":
                return FacultyService.findAllOrderByAllPlacesDesc();
            case "budgetPlacesDesc":
                return FacultyService.findAllOrderByBudgetPlacesDesc();
            case "nameAsc":
            default:
                return FacultyService.findAllOrderByNameAsc();
        }
    }
}
