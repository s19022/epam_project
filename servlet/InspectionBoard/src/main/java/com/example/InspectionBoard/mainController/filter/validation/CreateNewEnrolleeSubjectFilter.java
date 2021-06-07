package com.example.InspectionBoard.mainController.filter.validation;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.InspectionBoard.Constants.*;
import static com.example.InspectionBoard.mainController.Utils.getInt;
import static com.example.InspectionBoard.mainController.filter.FilterUtils.isValid;

public class CreateNewEnrolleeSubjectFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig){ }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String subjectName = request.getParameter(SUBJECT_NAME);
        if (!isValid(subjectName)){
            response.sendError(400);
            return;
        }
        int mark = getInt(request.getParameter(MARK));
        request.setAttribute(SUBJECT_NAME, subjectName);
        request.setAttribute(MARK, mark);
        filterChain.doFilter(request, response);
    }


    @Override
    public void destroy() { }


}
