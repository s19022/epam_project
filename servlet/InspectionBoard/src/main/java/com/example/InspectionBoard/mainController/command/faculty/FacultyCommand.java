package com.example.InspectionBoard.mainController.command.faculty;

import com.example.InspectionBoard.mainController.command.Command;
import com.example.InspectionBoard.mainController.command.RequestType;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.service.FacultyService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;

public class FacultyCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, RequestType type) {
        setFaculties(request, getAll());
        return REDIRECT_KEYWORD + "/faculty/main.jsp";
    }

    private List<Faculty> getAll(){
        return FacultyService.findAll();
    }

    private static void setFaculties(HttpServletRequest request, List<Faculty> faculties){
        request.getSession().setAttribute("faculties", faculties);
    }
}
