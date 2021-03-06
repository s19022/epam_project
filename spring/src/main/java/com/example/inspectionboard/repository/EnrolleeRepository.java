package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.Enrollee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface EnrolleeRepository extends CrudRepository<Enrollee, Long>, PagingAndSortingRepository<Enrollee, Long> {
    @Query("FROM Enrollee e left join fetch e.enrolleeSubjectSet left join fetch e.facultyRegistrationSet WHERE e.login = ?1")
    Optional<Enrollee> findByLogin(String login);
}

