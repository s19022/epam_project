package com.example.InspectionBoard.servlet;

import org.apache.logging.log4j.core.util.FileUtils;
import org.apache.logging.log4j.core.util.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

@WebServlet(name = "errorServlet", value = "/error/*")
public class ErrorServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
//        req.
    }
}
