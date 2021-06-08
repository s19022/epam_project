package com.example.InspectionBoard.mainController.filter.validation;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.mainController.Utils.isValid;

public class CreateNewFacultyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String facultyName = request.getParameter(FACULTY_NAME);
        int allPlaces;
        int budgetPlaces;
        if (!isValid(facultyName)){
            response.sendError(400);
            return;
        }
        try{
            allPlaces = Integer.parseInt(request.getParameter(ALL_PLACES));
            budgetPlaces = Integer.parseInt(request.getParameter(BUDGET_PLACES));
        }catch (NumberFormatException ex){
            response.sendError(400);
            return;
        }
        request.setAttribute(FACULTY_NAME, facultyName);
        request.setAttribute(ALL_PLACES, allPlaces);
        request.setAttribute(BUDGET_PLACES, budgetPlaces);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
