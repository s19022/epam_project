package com.example.InspectionBoard.mainController.command;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Map;

public class CommandUtility {
    public static boolean isLoggedIn(HashSet<String> loggedUsers, String login){
        if (login == null){
            return false;
        }
        return loggedUsers.stream().anyMatch(login::equals);
    }

    public static void printParameters(HttpServletRequest request){
        for (Map.Entry<String, String[]> item : request.getParameterMap().entrySet()){
            System.out.println(item.getKey() + ", " + item.getValue()[0]);
        }
    }
}
