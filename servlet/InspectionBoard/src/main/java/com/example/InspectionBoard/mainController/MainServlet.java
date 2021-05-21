package com.example.InspectionBoard.mainController;


import com.example.InspectionBoard.mainController.command.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

import static com.example.InspectionBoard.Constants.*;

public class MainServlet extends HttpServlet {
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig config){
        config.getServletContext().setAttribute(LOGGED_USERS, new HashSet<Integer>());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        processRequest(request, response, RequestType.POST);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response, RequestType.GET);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response, RequestType requestType) throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/InspectionBoard_war/" , "");
        Command command = commands.getOrDefault(path,
                (r, i)-> DEFAULT_PATH);
        String page = command.execute(request, requestType);
        System.out.println("redirect to, " + page);
        System.out.println("type, " + requestType.name());
        if(page.contains("redirect:")){
            response.sendRedirect(page.replace(REDIRECT_KEYWORD, "/InspectionBoard_war"));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
