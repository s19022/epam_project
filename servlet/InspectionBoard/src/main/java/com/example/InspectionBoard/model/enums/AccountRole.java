package com.example.InspectionBoard.model.enums;

public enum AccountRole {
    ENROLLEE {
        @Override
        public String getRedirectPath(){
            return /*"/WEB-INF"*/"/enrollee/main.jsp";
        }
    },
    ADMIN{
        @Override
        public String getRedirectPath(){
            return /*"/WEB-INF"*/"/admin/main.jsp";
        }
    },
    UNKNOWN;

    public String getRedirectPath(){
        return "/index.jsp";
    }
}
