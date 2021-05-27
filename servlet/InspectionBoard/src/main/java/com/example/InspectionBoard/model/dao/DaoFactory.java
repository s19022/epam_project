package com.example.InspectionBoard.model.dao;


import com.example.InspectionBoard.model.dao.implementation.JDBCFactory;

import java.sql.Connection;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract AccountDao createAccountDao();
    public abstract FacultyDao createFacultyDao();
    public abstract SubjectDao createSubjectDao();
    public abstract RequiredSubjectDao createRequiredSubjectDao();
    public abstract EnrolleeSubjectDao createEnrolleeSubjectDao();
    public abstract FacultyRegistrationDao createFacultyRegistrationDao();

    public abstract AccountDao createAccountDao(Connection connection);
    public abstract FacultyDao createFacultyDao(Connection connection);
    public abstract SubjectDao createSubjectDao(Connection connection);
    public abstract RequiredSubjectDao createRequiredSubjectDao(Connection connection);
    public abstract EnrolleeSubjectDao createEnrolleeSubjectDao(Connection connection);
    public abstract FacultyRegistrationDao createFacultyRegistrationDao(Connection connection);

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
