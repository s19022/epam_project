package com.example.inspectionboard.controller;

import com.example.inspectionboard.exception.*;
import com.example.inspectionboard.model.enums.*;
import com.example.inspectionboard.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
    public static final String ENROLLEE_LIST = "enrollee";
    public static final String PAGE_NUMBER = "pageNumber";
    public static final String REGISTRATION_LIST = "registrationList";
    public static final String ITEMS_PER_PAGE = "itemsPerPage";
    public static final String NUMBER_OF_PAGES = "numberOfPages";

    private final FacultyRegistrationService facultyRegistrationService;
    private final EnrolleeService enrolleeService;

    @RequestMapping("/main")
    public String mainPage(Model model){
        model.addAttribute(REGISTRATION_LIST, facultyRegistrationService.findAllPending());
        return "admin/main";
    }

    @RequestMapping("/changeRegistrationStatus")
    public String changeFacultyRegistration(@RequestParam FacultyRegistrationStatus newStatus,
                                            @RequestParam String enrolleeLogin,
                                            @RequestParam String facultyName){
        System.out.println(newStatus);
        try {
            facultyRegistrationService.changeStatus(newStatus, enrolleeLogin, facultyName);
        } catch (NoSuchFacultyException | NoSuchEnrolleeException | NotEnoughPlacesException e) {
            e.printStackTrace();
        }
        return "redirect:/admin/main";
    }

    @RequestMapping("/enrollee")
    public String enrolleePage(Model model, @RequestParam int pageNumber, @RequestParam int itemsPerPage){
        var enrollees = enrolleeService.findAll(pageNumber, itemsPerPage);
        model.addAttribute(ITEMS_PER_PAGE, itemsPerPage);
        model.addAttribute(NUMBER_OF_PAGES, 2);
        model.addAttribute(PAGE_NUMBER, pageNumber);
        model.addAttribute(ENROLLEE_LIST, enrollees.toList());
        System.out.println(enrollees.getTotalElements());
        System.out.println(enrollees.getTotalPages());
        System.out.println(enrollees.getNumber());
        System.out.println(enrollees);
        return "admin/enrolleeInfo";
    }
}
