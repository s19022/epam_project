package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.EnrolleeSubject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface EnrolleeSubjectRepository extends CrudRepository<EnrolleeSubject, Long> {
    Set<EnrolleeSubject> getAllByEnrolleeId(Long id);
    Set<EnrolleeSubject> getAllByEnrolleeLogin(String login);
}
