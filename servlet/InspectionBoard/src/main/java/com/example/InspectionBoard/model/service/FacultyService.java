package com.example.InspectionBoard.model.service;

import com.example.InspectionBoard.exceptions.*;
import com.example.InspectionBoard.model.dao.DaoFactory;
import com.example.InspectionBoard.model.dao.FacultyDao;
import com.example.InspectionBoard.model.dto.CreateFacultyDto;
import com.example.InspectionBoard.model.dto.ModifyFacultyDto;
import com.example.InspectionBoard.model.entity.Faculty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

import static com.example.InspectionBoard.Constants.SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE;
import static com.example.InspectionBoard.model.dto.db.Mapper.toFaculty;
import static com.example.InspectionBoard.model.service.ServiceUtility.isValid;

public class FacultyService {
    private static final Logger LOGGER = LogManager.getLogger(FacultyService.class.getName());

    /**
     *
     * @param dto data to be updated
     * @throws BudgetPlacesBiggerThanAllPlacesException if budgetPlaces > than allPlaces
     */
    public void update(ModifyFacultyDto dto) throws BudgetPlacesBiggerThanAllPlacesException {
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            if(dto.getBudgetPlaces() > dto.getAllPlaces()){
                throw new BudgetPlacesBiggerThanAllPlacesException();
            }
            dao.update(dto);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     *
     * @param dto data to be inserted
     * @throws FacultyNameIsTakenException if faculty with given name already exists
     * @throws BudgetPlacesBiggerThanAllPlacesException if budgetPlaces > than allPlace
     */
    public void create(CreateFacultyDto dto) throws FacultyNameIsTakenException, BudgetPlacesBiggerThanAllPlacesException {
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            if(dto.getBudgetPlaces() > dto.getAllPlaces()){
                throw new BudgetPlacesBiggerThanAllPlacesException();
            }
            dao.create(dto);
        }catch (SQLException ex){
            if(SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE.equals(ex.getSQLState())){
                throw new FacultyNameIsTakenException();
            }
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     *
     * @return all faculties ordered by name descending
     */
    public List<Faculty> findAllOrderByNameDesc(){
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            return toFaculty(dao.findAllOrderByNameDesc());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }


    /**
     *
     * @return all faculties ordered by name ascending
     */
    public List<Faculty> findAllOrderByNameAsc(){
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            return toFaculty(dao.findAllOrderByNameAsc());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }


    /**
     *
     * @return all faculties ordered by budget places descending
     */
    public List<Faculty> findAllOrderByBudgetPlacesDesc(){
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            return toFaculty(dao.findAllOrderByBudgetPlacesDesc());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }


    /**
     *
     * @return all faculties ordered by all places descending
     */
    public List<Faculty> findAllOrderByAllPlacesDesc(){
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            return toFaculty(dao.findAllOrderByAllPlacesDesc());
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     * sets isDeleted to true for faculty with given name
     * @param facultyName name of the faculty to be deleted
     */
    public void deleteByFacultyName(String facultyName){
        if (!isValid(facultyName)){
            LOGGER.warn("Empty faculty name");
            return;
        }
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            dao.deleteByFacultyName(facultyName);
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }

    /**
     *
     * @param name name of faculty to be found
     * @return found faculty
     * @throws NoSuchFacultyException if given faculty doesn't exist
     */
    public Faculty findByName(String name) throws NoSuchFacultyException {
        if (!isValid(name)){
            throw new NoSuchFacultyException();
        }
        try(FacultyDao dao = DaoFactory.getInstance().createFacultyDao()){
            return toFaculty(dao.findByName(name).orElseThrow(NoSuchFacultyException::new));
        }catch (SQLException ex){
            LOGGER.error(ex);
            throw new SQLExceptionWrapper(ex);
        }
    }
}
