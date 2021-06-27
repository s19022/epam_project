package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.*;
import com.example.inspectionboard.model.enums.FacultyRegistrationStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface FacultyRegistrationRepository extends CrudRepository<FacultyRegistration, Long> {
    Set<FacultyRegistration> findAllByStatusAndFacultyIsDeleted(FacultyRegistrationStatus status, boolean isDeleted);

    Set<FacultyRegistration> findAllByEnrolleeLoginAndFacultyIsDeleted(String enrolleeLogin, boolean isDeleted);

    @Modifying
    @Query("UPDATE FacultyRegistration  r SET r.status = ?3 WHERE r.faculty = ?2 AND r.enrollee =?1")
    int updateSetStatus(Enrollee enrollee, Faculty faculty, FacultyRegistrationStatus status);
}

