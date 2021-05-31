package com.example.InspectionBoard.mainController;


import com.example.InspectionBoard.mainController.command.*;
import com.example.InspectionBoard.mainController.command.admin.AdminEnrolleeCommand;
import com.example.InspectionBoard.mainController.command.admin.AdminMainCommand;
import com.example.InspectionBoard.mainController.command.admin.BlockEnrolleeCommand;
import com.example.InspectionBoard.mainController.command.admin.UnblockEnrolleeCommand;
import com.example.InspectionBoard.mainController.command.enrollee.EnrolleeMainCommand;
import com.example.InspectionBoard.mainController.command.faculty.DeleteFacultyCommand;
import com.example.InspectionBoard.mainController.command.faculty.FacultyCommand;
import com.example.InspectionBoard.mainController.command.faculty.FacultyInfoCommand;
import com.example.InspectionBoard.mainController.command.faculty.RegisterToFacultyCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

import static com.example.InspectionBoard.Constants.*;

public class MainServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(MainServlet.class.getName());
    private final Map<String, Command> commands = new HashMap<>();

    @Override
    public void init(ServletConfig config){
        config.getServletContext().setAttribute(LOGGED_USERS, new HashSet<Integer>());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());

        commands.put("admin/main", new AdminMainCommand());
        commands.put("admin/enrollees", new AdminEnrolleeCommand());
        commands.put("admin/enrollees/block", new BlockEnrolleeCommand());
        commands.put("admin/enrollees/unblock", new UnblockEnrolleeCommand());

        commands.put("faculties", new FacultyCommand());
        commands.put("faculties/register", new RegisterToFacultyCommand());
        commands.put("faculties/info", new FacultyInfoCommand());
        commands.put("faculties/delete", new DeleteFacultyCommand());

        commands.put("enrollee/main", new EnrolleeMainCommand());
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        processRequest(request, response, RequestType.POST);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response, RequestType.GET);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response,
                                RequestType requestType) throws ServletException, IOException {
        String path = request.getRequestURI();
        path = path.replaceAll(".*/" + APP_NAME +"/" , "");
        Command command = commands.getOrDefault(path,
                (r, i)-> REDIRECT_KEYWORD + DEFAULT_PATH);
        String page = command.execute(request, requestType);

        LOGGER.info(requestType.name() + ": " + path + " -> " + page);

        if(page.contains("redirect:")){
            response.sendRedirect(page.replace(REDIRECT_KEYWORD, "/" + APP_NAME));
        }else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}
