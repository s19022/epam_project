package com.example.inspectionboard.service;

import com.example.inspectionboard.exception.MarkIsNotValidException;
import com.example.inspectionboard.exception.NoSuchFacultyException;
import com.example.inspectionboard.exception.NoSuchSubjectException;
import com.example.inspectionboard.exception.NotUniqueSubjectException;
import com.example.inspectionboard.model.RequiredSubject;
import com.example.inspectionboard.repository.FacultyRepository;
import com.example.inspectionboard.repository.RequiredSubjectRepository;
import com.example.inspectionboard.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.inspectionboard.service.ServiceUtils.isMarkValid;

@Service
@RequiredArgsConstructor
public class RequiredSubjectService {
    private final FacultyRepository facultyRepository;
    private final SubjectRepository subjectRepository;
    private final RequiredSubjectRepository requiredSubjectRepository;
    /**
     * saves EnrolleeSubject to database
     *
     * @throws NotUniqueSubjectException if Enrollee already filled given subject
     * @throws NoSuchSubjectException    if Subject with given name is not present in database
     * @throws MarkIsNotValidException   if mark is greater than MAX_MARK or smaller than MIN_MARK
     */
    @Transactional
    public void save(String subjectName, String facultyName, int requiredGrade) throws MarkIsNotValidException, NoSuchSubjectException, NoSuchFacultyException, NotUniqueSubjectException {
        if(!isMarkValid(requiredGrade)){
            throw new MarkIsNotValidException();
        }
        var faculty = facultyRepository.findFacultyByName(facultyName).orElseThrow(NoSuchFacultyException::new);
        var subject = subjectRepository.findByName(subjectName).orElseThrow(NoSuchSubjectException::new);
        if(faculty.getRequiredSubjectSet().stream().map(RequiredSubject::getSubject).anyMatch(s -> s.equals(subject))){
            throw new NotUniqueSubjectException();
        }
        var requiredSubject = RequiredSubject.builder()
                .subject(subject)
                .faculty(faculty)
                .requiredGrade(requiredGrade)
                .build();
        requiredSubjectRepository.save(requiredSubject);
    }
}