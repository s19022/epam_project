package com.example.InspectionBoard.model.dao;


public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract AccountDao createAccountDao();
    public abstract FacultyDao createFacultyDao();
    public abstract SubjectDao createSubjectDao();

    public static DaoFactory getInstance(){
        if (daoFactory == null){
            synchronized (DaoFactory.class){
                if (daoFactory == null){

                }
            }
        }
        return daoFactory;
    }
}
