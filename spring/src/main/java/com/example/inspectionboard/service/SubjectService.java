package com.example.inspectionboard.service;

import com.example.inspectionboard.model.Enrollee;
import com.example.inspectionboard.model.Faculty;
import com.example.inspectionboard.model.Subject;
import com.example.inspectionboard.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectRepository subjectRepository;

    public Set<Subject> findNotTakenByEnrollee(@NotNull Enrollee enrollee){
        return subjectRepository.findNotTakenByEnrollee(enrollee);
    }

    public Set<Subject> findNotTakenByFaculty(@NotNull Faculty faculty){
        return subjectRepository.findNotTakenByFaculty(faculty);
    }
}
