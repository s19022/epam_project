package com.example.InspectionBoard.mainController.command;

import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.entity.Faculty;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.InspectionBoard.Constants.REDIRECT_KEYWORD;

public class FacultyCommand implements Command{
    @Override
    public String execute(HttpServletRequest request, RequestType type) {
        List<Faculty> faculties = DaoFactory.getInstance().createFacultyDao().findAll();
        request.getSession().setAttribute("faculties", faculties);
        return REDIRECT_KEYWORD + "/facultyF/main.jsp";
    }

}
