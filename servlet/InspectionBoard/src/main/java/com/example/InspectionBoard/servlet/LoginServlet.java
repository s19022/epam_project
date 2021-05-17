package com.example.InspectionBoard.servlet;

import com.example.InspectionBoard.command.Command;
import com.example.InspectionBoard.command.LoginCommand;
import com.example.InspectionBoard.command.LogoutCommand;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "loginServlet", value = "/")
public class LoginServlet extends HttpServlet {
    public static final String LOGGED_USERS = "loggedUsers";
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig config){
        config.getServletContext().setAttribute(LOGGED_USERS, new HashSet<Integer>());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        request.getRequestDispatcher("/login.jsp").forward(request, response);
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/InspectionBoard_war/" , "");
        Command command = commands.getOrDefault(path ,
                (r)->"/index.jsp");
        String page = command.execute(request);
        request.getRequestDispatcher(page).forward(request, response);

       /* Locale l = new Locale("uk", "UA");
        System.out.println(l);
        ResourceBundle b = ResourceBundle.getBundle("res", l);
        System.out.println(b.getString("apple"));
    */}
}
