package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.SQLExceptionWrapper;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.EnrolleeDao;
import com.example.InspectionBoard.model.dto.db.DbEnrolleeDto;
import com.example.InspectionBoard.model.dto.db.FindByPageDto;
import com.example.InspectionBoard.model.dto.db.Mapper;
import com.example.InspectionBoard.model.entity.Enrollee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class EnrolleeService {
    private static final Logger LOGGER = LogManager.getLogger(EnrolleeService.class.getName());

    public List<Enrollee> findAllByPage(FindByPageDto page){
        int limit = page.getItemsPerPage();
        int offset = page.getItemsPerPage() * (page.getPageNumber() - 1);
        try(EnrolleeDao dao = DaoFactory.getInstance().createEnrolleeDao()){
            List<DbEnrolleeDto> dtoList = dao.findAllLimitAndOffset(limit, offset);
            return Mapper.toEnrollee(dtoList);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    public int getNumberOfPages(double itemsPerPage){
        try(EnrolleeDao dao = DaoFactory.getInstance().createEnrolleeDao()){
            return (int) Math.ceil(dao.getNumberOfEnrollees() / itemsPerPage);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
