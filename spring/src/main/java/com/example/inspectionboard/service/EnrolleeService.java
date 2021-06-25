package com.example.inspectionboard.service;

import com.example.inspectionboard.exception.*;
import com.example.inspectionboard.model.Enrollee;
import com.example.inspectionboard.repository.EnrolleeRepository;
import com.example.inspectionboard.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrolleeService {
    private final EnrolleeRepository studentEnrolleeRepository;
    private final FacultyRepository facultyRepository;

    public Enrollee findByLogin(String login) throws NoSuchEnrolleeException {
        return studentEnrolleeRepository.findByLogin(login).orElseThrow(NoSuchEnrolleeException::new);
    }

}
