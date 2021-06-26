package com.example.inspectionboard.controller;

import com.example.inspectionboard.exception.NoSuchEnrolleeException;
import com.example.inspectionboard.service.EnrolleeService;
import com.example.inspectionboard.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping(value = "/enrollee")
@RequiredArgsConstructor
public class EnrolleeController {
    public static final String SUBJECTS = "subjects";
    public static final String REGISTERED_FACULTIES = "registeredFaculties";
    public static final String NOT_TAKEN_SUBJECTS = "notTakenSubjects";

    private final EnrolleeService enrolleeService;
    private final SubjectService subjectService;

    @GetMapping(value = "/main")
    public String mainPage(Model model, Authentication authentication){
        try {
            var enrollee = enrolleeService.findByLogin(authentication.getName());
            var notTakenSubjects = subjectService.findNotTakenByEnrolleeLogin(enrollee);
            model.addAttribute(SUBJECTS, enrollee.getEnrolleeSubjectSet());
            model.addAttribute(REGISTERED_FACULTIES, enrollee.getFacultyRegistrationSet());
            model.addAttribute(NOT_TAKEN_SUBJECTS, notTakenSubjects);
        } catch (NoSuchEnrolleeException e) {
            e.printStackTrace();
        }
        return "enrollee/main";
    }
}

