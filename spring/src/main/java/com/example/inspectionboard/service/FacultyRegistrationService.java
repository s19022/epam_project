package com.example.inspectionboard.service;

import com.example.inspectionboard.exception.*;
import com.example.inspectionboard.model.Enrollee;
import com.example.inspectionboard.model.Faculty;
import com.example.inspectionboard.model.FacultyRegistration;
import com.example.inspectionboard.model.RequiredSubject;
import com.example.inspectionboard.model.enums.FacultyRegistrationStatus;
import com.example.inspectionboard.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FacultyRegistrationService {
    private static final String SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE = "23505";
    private final RequiredSubjectRepository requiredSubjectRepository;
    private final EnrolleeSubjectRepository passedSubjectRepository;
    private final FacultyRepository facultyRepository;
    private final EnrolleeRepository studentEnrolleeRepository;
    private final FacultyRegistrationRepository facultyRegistrationRepository;

    /**
     *
     * @param newStatus information about facultyRegistration to be updated
     * @throws NotEnoughPlacesException if faculty with given name doesn't have free places
     * @throws NoSuchFacultyException if there are no faculty with given name
     */
    @Transactional
    public void changeStatus(FacultyRegistrationStatus newStatus, String enrolleeLogin, String facultyName) throws /*NotEnoughPlacesException,*/ NoSuchFacultyException, NoSuchEnrolleeException, NotEnoughPlacesException {
        var enrollee = studentEnrolleeRepository.findByLogin(enrolleeLogin).orElseThrow(NoSuchEnrolleeException::new);
        var faculty = facultyRepository.findFacultyByName(facultyName).orElseThrow(NoSuchFacultyException::new);
        switch (newStatus){
            case PENDING:
                return;
            case REJECTED:
                facultyRegistrationRepository.updateSetStatus(enrollee, faculty, newStatus);
                return;
            case ACCEPTED_CONTRACT:
                changeStatusToContract(enrollee, faculty);
                return;
            case ACCEPTED_BUDGET:
                changeStatusToBudget(enrollee, faculty);
        }
    }

    public Set<FacultyRegistration> findAllPending(){
        return facultyRegistrationRepository.findAllByStatusAndFacultyIsDeleted(FacultyRegistrationStatus.PENDING, false);
    }

    /**
     *  saves Enrollee with given login to faculty with given name
     * @param enrolleeLogin account Login
     * @param facultyName faculty name
     * @throws NoSuchEnrolleeException if Account with given login doesn't exist in database
     * @throws NoSuchFacultyException if Faculty with given name doesn't exist in database
     * @throws CannotRegisterToFacultyException if Enrollee doesn't meet requirements for given faculty
     * @throws AlreadyRegisteredException if Enrollee already saved to given faculty
     */
    public void register(String enrolleeLogin, String facultyName) throws NoSuchEnrolleeException, NoSuchFacultyException, CannotRegisterToFacultyException, AlreadyRegisteredException {
        var enrollee = studentEnrolleeRepository.findByLogin(enrolleeLogin).orElseThrow(NoSuchEnrolleeException::new);
        var faculty = facultyRepository.findFacultyByName(facultyName).orElseThrow(NoSuchFacultyException::new);

        if (!canRegister(enrollee, faculty)){
            throw new CannotRegisterToFacultyException();
        }
        var registration = FacultyRegistration.builder().faculty(faculty).enrollee(enrollee).build();
        saveRegistration(registration);
    }


    /**
     *  Compares required marks and subjects of faculty to data passed by enrollee
     * @param enrollee Enrollee to be checked
     * @param faculty  Faculty to be checked against
     * @return whether enrollee meets requirements to register to faculty
     */
    private boolean canRegister(Enrollee enrollee, Faculty faculty){
        var passedSubjects = passedSubjectRepository.getAllByEnrolleeId(enrollee.getId());
        var requiredSubjects = requiredSubjectRepository.findAllByFacultyId(faculty.getId());
        for (RequiredSubject subject : requiredSubjects){
            boolean contains = passedSubjects.stream().anyMatch(s -> subject.getSubject().equals(s.getSubject())
                    && subject.getRequiredGrade() <= s.getMark());
            if (!contains){
                return false;
            }
        }
        return true;
    }

    private void saveRegistration(FacultyRegistration registration) throws AlreadyRegisteredException {
        try{
            facultyRegistrationRepository.save(registration);
        }catch (DataIntegrityViolationException ex) {
            var cause = NestedExceptionUtils.getMostSpecificCause(ex);
            if (!(cause instanceof SQLException)) {
                throw ex;
            }
            var sqlException = (SQLException) cause;
            if (SQL_BREAKING_UNIQUE_CONSTRAINT_ERROR_CODE.equals(sqlException.getSQLState())) {
                throw new AlreadyRegisteredException();
            }
        }
    }

    private void changeStatusToContract(Enrollee enrollee, Faculty faculty) throws NotEnoughPlacesException {
        if ((faculty.getAllPlaces() - faculty.getBudgetPlaces()) <= 0){
            throw new NotEnoughPlacesException();
        }
        facultyRegistrationRepository.updateSetStatus(enrollee, faculty, FacultyRegistrationStatus.ACCEPTED_CONTRACT);
        facultyRepository.updateSetAllPlaces(faculty, faculty.getAllPlaces() - 1);
    }

    private void changeStatusToBudget(Enrollee enrollee, Faculty faculty) throws NotEnoughPlacesException {
        if (faculty.getBudgetPlaces() <= 0){
            throw new NotEnoughPlacesException();
        }
        facultyRegistrationRepository.updateSetStatus(enrollee, faculty, FacultyRegistrationStatus.ACCEPTED_BUDGET);
        facultyRepository.updateSetAllPlacesAndBudgetPlaces(faculty, faculty.getAllPlaces() - 1, faculty.getBudgetPlaces() - 1);
    }
}

