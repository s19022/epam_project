package com.example.InspectionBoard.servlet;

import com.example.InspectionBoard.entity.Faculty;
import com.example.InspectionBoard.entity.Subject;
import com.example.InspectionBoard.repository.FacultyRepository;
import com.example.InspectionBoard.repository.SubjectRepository;
import exceptions.ParsingException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "facultyServlet", value = "/faculty")
public class FacultyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        try{
            List<Faculty> facultyList = FacultyRepository.getInstance().getAllFaculties();
            out.println(facultyList);
        }catch (ParsingException ex){
            response.sendError(500);
        }

*/    }
}
