package com.example.InspectionBoard.exceptions;

public class SQLExceptionWrapper extends RuntimeException{
    public SQLExceptionWrapper(Throwable cause) {
        super(cause);
    }
}
