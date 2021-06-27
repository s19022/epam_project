package com.example.inspectionboard.controller;

import com.example.inspectionboard.exception.*;
import com.example.inspectionboard.model.enums.AccountType;
import com.example.inspectionboard.service.FacultyRegistrationService;
import com.example.inspectionboard.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

import static com.example.inspectionboard.Constants.*;

@Controller
@RequestMapping(value = "/faculties")
@RequiredArgsConstructor
public class FacultyController {
    public static final String SUCCESSFULLY = "SUCCESSFULLY";
    private final FacultyService facultyService;
    private final FacultyRegistrationService facultyRegistrationService;

    @GetMapping()
    public String mainPage(Model model,
                           Authentication authentication,
                           @RequestParam(defaultValue = NAME_ASC) String facultyOrder){
        model.addAttribute(USER_ROLE, getRole(authentication));
        model.addAttribute(FACULTIES, facultyService.findAllOrderBy(facultyOrder));
        model.addAttribute(FACULTY_ORDER, facultyOrder);
        return "faculty/main";
    }

    @RequestMapping(value = "/info")
    public String infoPage(Model model, Authentication authentication, @RequestParam String facultyName){
        try{
            model.addAttribute(USER_ROLE, getRole(authentication));
            model.addAttribute(FACULTY_INFO, facultyService.findFacultyByName(facultyName));
        } catch (NoSuchFacultyException e) {
            e.printStackTrace();
        }
        return "faculty/info";
    }

    @RequestMapping("/register")
    public String register(Model model, Authentication authentication, @RequestParam String facultyName){
        String status = SUCCESSFULLY;
        try {
            facultyRegistrationService.register(authentication.getName(), facultyName);
        } catch (NoSuchEnrolleeException | NoSuchFacultyException | CannotRegisterToFacultyException | AlreadyRegisteredException e) {
            status = e.getClass().getName();
        }
        model.addAttribute(FACULTY_REGISTRATION_STATUS, status);
        return infoPage(model, authentication, facultyName);
    }

    private static GrantedAuthority getRole(Authentication authentication){
        if (authentication == null){
            return AccountType.UNKNOWN;
        }
        return new ArrayList<>(authentication.getAuthorities()).get(0);
    }
}
