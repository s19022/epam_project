package com.example.InspectionBoard.mainController.command;

import java.util.HashSet;

public class CommandUtility {
    public static boolean isLoggedIn(HashSet<String> loggedUsers, String login){
        if (login == null){
            return false;
        }
        return loggedUsers.stream().anyMatch(login::equals);
    }
}
