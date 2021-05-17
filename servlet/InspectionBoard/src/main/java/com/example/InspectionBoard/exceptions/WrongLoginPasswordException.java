package com.example.InspectionBoard.exceptions;

public class WrongLoginPasswordException extends Exception{
    public WrongLoginPasswordException(String message) {
        super(message);
    }
}
