package com.example.InspectionBoard;

import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        Locale l = new Locale("uk", "UA");
        System.out.println(l);
        ResourceBundle b = ResourceBundle.getBundle("res", l);
        System.out.println(b.getString("apple"));
    }
}
