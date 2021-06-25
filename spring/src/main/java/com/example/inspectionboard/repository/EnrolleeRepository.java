package com.example.inspectionboard.repository;

import com.example.inspectionboard.model.Enrollee;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EnrolleeRepository extends CrudRepository<Enrollee, Long> {
    Optional<Enrollee> findByLogin(String login);
}

