package com.example.inspectionboard.controller;

import com.example.inspectionboard.model.enums.AccountType;
import com.example.inspectionboard.service.FacultyService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import static com.example.inspectionboard.Constants.*;

@Controller
@RequestMapping(value = "/faculties")
@RequiredArgsConstructor
public class FacultyController {
    private final FacultyService facultyService;

    @GetMapping()
    public String mainPage(Model model,
                           Authentication authentication,
                           @RequestParam(defaultValue = NAME_ASC) String facultyOrder){
        model.addAttribute(USER_ROLE, getRole(authentication));
        model.addAttribute(FACULTIES, facultyService.findAllOrderBy(facultyOrder));
        model.addAttribute(FACULTY_ORDER, facultyOrder);
        return "faculty/main";
    }

    private static GrantedAuthority getRole(Authentication authentication){
        if (authentication == null){
            return AccountType.UNKNOWN;
        }
        return new ArrayList<>(authentication.getAuthorities()).get(0);
    }
}
