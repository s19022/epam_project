package com.example.inspectionboard.controller;

import com.example.inspectionboard.model.enums.AccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class LoginController {
    @RequestMapping
    public String defaultPage(Authentication authentication){
        var role = (AccountType) new ArrayList<GrantedAuthority>(authentication.getAuthorities()).get(0);
        switch (role){
            case ADMIN:
                return "redirect:/admin/main";
            case ENROLLEE:
            default:
                return "redirect:/enrollee/main";
        }
    }
}

