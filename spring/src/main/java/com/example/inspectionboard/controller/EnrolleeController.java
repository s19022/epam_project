package com.example.inspectionboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/enrollee")
@RequiredArgsConstructor
public class EnrolleeController {
    public static final String SUBJECTS = "subjects";
    public static final String REGISTERED_FACULTIES = "registeredFaculties";
    public static final String FACULTY = "faculty";
    public static final String ROLE = "role";
    public static final String STUDENT_ENROLLEE = "studentEnrollee";

//    private final StudentEnrolleeService studentEnrolleeService;

    @GetMapping(value = "/main")
    public String mainPage(Model model, Authentication authentication){
//        var studentEnrollee = studentEnrolleeService.findByLogin(authentication.getName());
//        model.addAttribute(STUDENT_ENROLLEE, studentEnrollee);
//        model.addAttribute(ROLE, studentEnrollee.getRole());
        return "enrollee/main";
    }

//    @PostMapping(value = "/apply")
//    public String apply(Authentication authentication, @RequestParam String facultyName){
//        try {
//            studentEnrolleeService.changeToStudent(authentication.getName(), facultyName);
//        } catch (NoSuchEnrolleeException | NoSuchFacultyException e) {
//            e.printStackTrace();
//        }
//        return "redirect:/studentEnrollee/main";
//    }
}

