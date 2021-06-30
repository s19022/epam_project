package com.example.inspectionboard.service;

import com.example.inspectionboard.exception.*;
import com.example.inspectionboard.model.Enrollee;
import com.example.inspectionboard.repository.EnrolleeRepository;
import com.example.inspectionboard.repository.FacultyRegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class EnrolleeService {
    private final EnrolleeRepository enrolleeRepository;
    private final FacultyRegistrationRepository facultyRegistrationRepository;

    @Transactional
    public Enrollee findByLogin(String login) throws NoSuchEnrolleeException {
        var enrollee = enrolleeRepository.findByLogin(login).orElseThrow(NoSuchEnrolleeException::new);
        var facultyRegistrations = facultyRegistrationRepository.findAllByEnrolleeLoginAndFacultyIsDeleted(enrollee.getLogin(), false);
        enrollee.setFacultyRegistrationSet(facultyRegistrations);
        return enrollee;
    }

    public Page<Enrollee> findAll(int pageNumber, int numberOfItems){
        Pageable pageable = PageRequest.of(pageNumber, numberOfItems);
        return enrolleeRepository.findAll(pageable);
    }
}
