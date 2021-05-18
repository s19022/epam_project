package com.example.InspectionBoard.model.enums;

public enum AccountRole {
    USER{
        @Override
        public String getRedirectPath(){
            return "/WEB-INF/enrollee/enrolleeBasic.jsp";
        }
    },
    ADMIN{
        @Override
        public String getRedirectPath(){
            return "/WEB-INF/admin/basic.jsp";
        }
    },
    UNKNOWN;

    public String getRedirectPath(){
        return "/index.jsp";
    }
}
