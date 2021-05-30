package com.example.InspectionBoard.model.enums;

public enum AccountRole {
    ENROLLEE {
        @Override
        public String getRedirectPath(){
            return "/enrollee/main";
        }
    },
    ADMIN{
        @Override
        public String getRedirectPath(){
            return "/admin/main";
        }
    },
    UNKNOWN;

    public String getRedirectPath(){
        return "/index.jsp";
    }
}
