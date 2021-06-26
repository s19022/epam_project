package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.Enrollee;
import com.example.inspectionboard.model.Subject;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface SubjectRepository extends CrudRepository<Subject, Long> {
    @Query("FROM Subject s WHERE s.id not in (SELECT e_s.id FROM EnrolleeSubject e_s WHERE e_s.enrollee = ?1)")
    Set<Subject> findNotTakenByEnrollee(Enrollee enrollee);
}
