package com.example.InspectionBoard.model.dao;


import com.example.InspectionBoard.model.dao.implementation.JDBCFactory;
import com.example.InspectionBoard.model.entity.RequiredSubject;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract AccountDao createAccountDao();
    public abstract FacultyDao createFacultyDao();
    public abstract SubjectDao createSubjectDao();
    public abstract RequiredSubjectDao createRequiredSubjectDao();

    public static DaoFactory getInstance(){
        if (daoFactory == null){
            synchronized (DaoFactory.class){
                if (daoFactory == null){
                    daoFactory = JDBCFactory.getInstance();
                }
            }
        }
        return daoFactory;
    }
}
