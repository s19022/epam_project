package com.example.inspectionboard.service;

import com.example.inspectionboard.exception.NoSuchFacultyException;
import com.example.inspectionboard.model.Faculty;
import com.example.inspectionboard.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.example.inspectionboard.Constants.*;

@Service
@RequiredArgsConstructor
public class FacultyService {
    private final FacultyRepository facultyRepository;

    public Set<Faculty> findAllOrderBy(String orderBy){
        switch (orderBy){
            case NAME_DESC:
                return facultyRepository.findAllByIsDeleted(Sort.by("name").descending(), false);
            case ALL_PLACES_DESC:
                return facultyRepository.findAllByIsDeleted(Sort.by("allPlaces").descending(), false);
            case BUDGET_PLACES_DESC:
                return facultyRepository.findAllByIsDeleted(Sort.by("budgetPlaces").descending(), false);
            case NAME_ASC:
            default:
                return facultyRepository.findAllByIsDeleted(Sort.by("name").ascending(), false);
        }
    }

    public Faculty findFacultyByName(String facultyName) throws NoSuchFacultyException {
        return facultyRepository.findFacultyByNameAndDeletedIs(facultyName, false).orElseThrow(NoSuchFacultyException::new);
    }

    @Transactional
    public void delete(String facultyName){
        facultyRepository.updateSetDeleted(facultyName, true);
    }
}

