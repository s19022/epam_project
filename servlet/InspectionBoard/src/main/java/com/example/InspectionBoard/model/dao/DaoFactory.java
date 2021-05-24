package com.example.InspectionBoard.model.dao;


import com.example.InspectionBoard.model.dao.implementation.JDBCFactory;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract AccountDao createAccountDao();
    public abstract FacultyDao createFacultyDao();
    public abstract SubjectDao createSubjectDao();

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
