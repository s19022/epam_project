package com.example.inspectionboard.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping(value = "/faculties")
@RequiredArgsConstructor
public class FacultyController {
    public static final String USER_ROLE = "userRole";
    //    private final FacultySe

    @GetMapping()
    public String mainPage(Model model, Authentication authentication){
        var role = new ArrayList<>(authentication.getAuthorities()).get(0);
        model.addAttribute(USER_ROLE, role);
        return "faculty/main";
    }
}
