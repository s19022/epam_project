package com.example.InspectionBoard.model.dao;


import com.example.InspectionBoard.model.dao.implementation.JDBCFactory;

import java.sql.Connection;

public abstract class DaoFactory {
    private static DaoFactory daoFactory;

    public abstract Connection getConnection();

    public abstract AccountDao createAccountDao(Connection connection);
    public abstract FacultyDao createFacultyDao(Connection connection);
    public abstract SubjectDao createSubjectDao(Connection connection);
    public abstract RequiredSubjectDao createRequiredSubjectDao(Connection connection);
    public abstract EnrolleeSubjectDao createEnrolleeSubjectDao(Connection connection);
    public abstract FacultyRegistrationDao createFacultyRegistrationDao(Connection connection);


    public AccountDao createAccountDao() {
        return createAccountDao(getConnection());
    }

    public FacultyDao createFacultyDao() {
        return createFacultyDao(getConnection());
    }

    public SubjectDao createSubjectDao() {
        return createSubjectDao(getConnection());
    }

    public RequiredSubjectDao createRequiredSubjectDao() {
        return createRequiredSubjectDao(getConnection());
    }

    public EnrolleeSubjectDao createEnrolleeSubjectDao() {
        return createEnrolleeSubjectDao(getConnection());
    }

    public FacultyRegistrationDao createFacultyRegistrationDao() {
        return createFacultyRegistrationDao(getConnection());
    }

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
