package com.example.inspectionboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    public static final String REGISTRATION_LIST = "registrationList";

//    private final FacultyRegistrationService facultyRegistrationService;

    @RequestMapping("/main")
    public String mainPage(Model model){
//        model.addAttribute(REGISTRATION_LIST, facultyRegistrationService.findAllPending());
        return "admin/main";
    }
/*
    @RequestMapping("/changeRegistrationStatus")
    public String changeFacultyRegistration(Model model,
                                            @RequestParam FacultyRegistrationStatus newStatus,
                                            @RequestParam String enrolleeLogin,
                                            @RequestParam String facultyName){
        System.out.println(newStatus);
        try {
            facultyRegistrationService.changeStatus(newStatus, enrolleeLogin, facultyName);
        } catch (NoSuchFacultyException | NoSuchEnrolleeException | NotEnoughPlacesException e) {
            e.printStackTrace();
        }
        return "redirect:/teacher/main";
    }*/
}
