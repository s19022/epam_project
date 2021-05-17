package com.example.InspectionBoard.command;

import java.util.HashSet;

public class CommandUtility {
    public static boolean isLoggedIn(HashSet<Integer> loggedUsers, Integer userId){
        if (userId == null){
            return false;
        }
        return loggedUsers.stream().anyMatch(userId::equals);
    }
}
