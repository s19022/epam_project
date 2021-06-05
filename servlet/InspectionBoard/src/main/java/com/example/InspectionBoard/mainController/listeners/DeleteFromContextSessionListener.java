package com.example.InspectionBoard.mainController.listeners;

import com.example.InspectionBoard.mainController.Utils;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class DeleteFromContextSessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) { }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        Utils.removeFromContext(se.getSession());
    }
}
