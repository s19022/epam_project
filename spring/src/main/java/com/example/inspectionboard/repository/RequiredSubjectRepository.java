package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.RequiredSubject;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface RequiredSubjectRepository extends CrudRepository<RequiredSubject, Long> {
    Set<RequiredSubject> findAllByFacultyId(Long facultyId);
    Set<RequiredSubject> findAllByFacultyName(String facultyName);
}
