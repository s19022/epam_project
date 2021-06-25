package com.example.inspectionboard.controller;

import com.example.inspectionboard.exception.NoSuchEnrolleeException;
import com.example.inspectionboard.service.EnrolleeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/enrollee")
@RequiredArgsConstructor
public class EnrolleeController {
    public static final String SUBJECTS = "subjects";
    public static final String REGISTERED_FACULTIES = "registeredFaculties";
    public static final String FACULTY = "faculty";
    public static final String ROLE = "role";
    public static final String STUDENT_ENROLLEE = "studentEnrollee";

    private final EnrolleeService enrolleeService;

    @GetMapping(value = "/main")
    public String mainPage(Model model, Authentication authentication){
        try {
            var enrollee = enrolleeService.findByLogin(authentication.getName());
            model.addAttribute(STUDENT_ENROLLEE, enrollee);
        } catch (NoSuchEnrolleeException e) {
            e.printStackTrace();
        }
        return "enrollee/main";
    }
}

