package com.example.InspectionBoard.entity;

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
    };

    public String getRedirectPath(){
        return "/index.jsp";
    }
}
