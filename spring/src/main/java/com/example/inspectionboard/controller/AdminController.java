package com.example.inspectionboard.controller;

import com.example.inspectionboard.exception.*;
import com.example.inspectionboard.model.enums.*;
import com.example.inspectionboard.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.inspectionboard.Constants.*;

@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminController {
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
    public String enrolleePage(Model model, @RequestParam(defaultValue = "1") int pageNumber, @RequestParam(defaultValue = "5") int itemsPerPage){
        var enrollees = enrolleeService.findAll(pageNumber, itemsPerPage);
        model.addAttribute(ITEMS_PER_PAGE, itemsPerPage);
        model.addAttribute(NUMBER_OF_PAGES, enrollees.getTotalPages());
        model.addAttribute(PAGE_NUMBER, enrollees.getNumber() + 1);
        model.addAttribute(ENROLLEE_LIST, enrollees.toList());
        return "admin/enrolleeInfo";
    }
}
