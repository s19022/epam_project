package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.exceptions.NoSuchFacultyException;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.entity.Faculty;
import com.example.InspectionBoard.model.service.FacultyService;
import com.example.InspectionBoard.model.service.SubjectService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class FacultyCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType type) {
        String name = request.getParameter("name");
        try{
            if (name == null){
                request.getSession().setAttribute("faculties", getAll());
            }else {
                request.getSession().setAttribute("faculties", getByName(name));
            }
        }catch (NoSuchFacultyException ex){
            request.setAttribute("search_result", ex);
        }
        return "/WEB-INF/faculty/main.jsp";
    }

    private List<Faculty> getAll(){
        return FacultyService.findAll();
    }

    private Faculty getByName(String name) throws NoSuchFacultyException {
        return FacultyService.getByName(name);
    }
}
