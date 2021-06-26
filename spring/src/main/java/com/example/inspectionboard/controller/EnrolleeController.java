package com.example.inspectionboard.controller;

import com.example.inspectionboard.exception.*;
import com.example.inspectionboard.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/enrollee")
@RequiredArgsConstructor
public class EnrolleeController {
    public static final String SUBJECTS = "subjects";
    public static final String REGISTERED_FACULTIES = "registeredFaculties";
    public static final String NOT_TAKEN_SUBJECTS = "notTakenSubjects";
    public static final String CREATE_ENROLLEE_SUBJECT_RESULT = "createEnrolleeSubjectResult";

    private final EnrolleeService enrolleeService;
    private final SubjectService subjectService;
    private final EnrolleeSubjectService enrolleeSubjectService;

    @GetMapping(value = "/main")
    public String mainPage(Model model, Authentication authentication){
        try {
            var enrollee = enrolleeService.findByLogin(authentication.getName());
            var notTakenSubjects = subjectService.findNotTakenByEnrollee(enrollee);
            model.addAttribute(SUBJECTS, enrollee.getEnrolleeSubjectSet());
            model.addAttribute(REGISTERED_FACULTIES, enrollee.getFacultyRegistrationSet());
            model.addAttribute(NOT_TAKEN_SUBJECTS, notTakenSubjects);
        } catch (NoSuchEnrolleeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "enrollee/main";
    }

    @PostMapping("/createSubject")
    public String createSubject(HttpServletRequest request,
                                Authentication authentication,
                                @RequestParam String subjectName,
                                @RequestParam int mark){
        try {
            enrolleeSubjectService.save(authentication.getName(), subjectName, mark);
        } catch (NoSuchSubjectException | MarkIsNotValidException | NotUniqueSubjectException e) {
            request.getSession().setAttribute(CREATE_ENROLLEE_SUBJECT_RESULT,e.getClass().getName());
        } catch (NoSuchEnrolleeException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return "redirect:/enrollee/main";
    }

}

