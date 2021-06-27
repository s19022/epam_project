package com.example.inspectionboard.service;

import com.example.inspectionboard.exception.*;
import com.example.inspectionboard.model.EnrolleeSubject;
import com.example.inspectionboard.repository.EnrolleeRepository;
import com.example.inspectionboard.repository.EnrolleeSubjectRepository;
import com.example.inspectionboard.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.inspectionboard.service.ServiceUtils.isMarkValid;


@Service
@RequiredArgsConstructor
public class EnrolleeSubjectService {
    private final EnrolleeSubjectRepository enrolleeSubjectRepository;
    private final EnrolleeRepository enrolleeRepository;
    private final SubjectRepository subjectRepository;

    /**
     * saves EnrolleeSubject to database
     *
     * @throws NotUniqueSubjectException if Enrollee already filled given subject
     * @throws NoSuchSubjectException    if Subject with given name is not present in database
     * @throws MarkIsNotValidException   if mark is greater than MAX_MARK or smaller than MIN_MARK
     */
    @Transactional
    public void save(String enrolleeLogin, String subjectName, int mark) throws NoSuchEnrolleeException, NoSuchSubjectException, MarkIsNotValidException, NotUniqueSubjectException {
        if (!isMarkValid(mark)) {
            throw new MarkIsNotValidException();
        }
        var enrollee = enrolleeRepository.findByLogin(enrolleeLogin).orElseThrow(NoSuchEnrolleeException::new);
        var subject = subjectRepository.findByName(subjectName).orElseThrow(NoSuchSubjectException::new);

        if(enrollee.getEnrolleeSubjectSet().stream().map(EnrolleeSubject::getSubject).anyMatch(s -> s.equals(subject))){
            throw new NotUniqueSubjectException();
        }

        var enrolleeSubject = EnrolleeSubject.builder()
                .subject(subject)
                .enrollee(enrollee)
                .mark(mark).build();
        enrolleeSubjectRepository.save(enrolleeSubject);
    }
}
