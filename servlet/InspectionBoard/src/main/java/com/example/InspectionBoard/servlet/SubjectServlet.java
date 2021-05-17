/*
package com.example.InspectionBoard.servlet;

import com.example.InspectionBoard.entity.Subject;
import com.example.InspectionBoard.repository.SubjectRepository;
import exceptions.ParsingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "subjectServlet", value = "/subject")
public class SubjectServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        try{
            List<Subject> subjects = SubjectRepository.getInstance().getSubjects();
            out.println(subjects);
        }catch (ParsingException ex){
            response.sendError(500);
        }
    }
}
*/
