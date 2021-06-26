package com.example.inspectionboard.service;

import com.example.inspectionboard.exception.NoSuchFacultyException;
import com.example.inspectionboard.model.Faculty;
import com.example.inspectionboard.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.example.inspectionboard.Constants.*;

@Service
@RequiredArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public Set<Faculty> findAllOrderBy(String orderBy){
        switch (orderBy){
            case NAME_DESC:
                return facultyRepository.findAllOrderByNameDesc();
            case ALL_PLACES_DESC:
                return facultyRepository.findAllOrderByAllPlacesDesc();
            case BUDGET_PLACES_DESC:
                return facultyRepository.findAllOrderByBudgetPlacesDesc();
            case NAME_ASC:
            default:
                return facultyRepository.findAllOrderByNameAsc();
        }
    }

    public Faculty findFacultyByName(String facultyName) throws NoSuchFacultyException {
        return facultyRepository.findFacultyByName(facultyName).orElseThrow(NoSuchFacultyException::new);
    }
}

