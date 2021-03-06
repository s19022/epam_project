package com.example.inspectionboard.controller;

import com.example.inspectionboard.exception.*;
import com.example.inspectionboard.model.enums.AccountType;
import com.example.inspectionboard.model.enums.FacultyRegistrationStatus;
import com.example.inspectionboard.service.FacultyRegistrationService;
import com.example.inspectionboard.service.FacultyService;
import com.example.inspectionboard.service.RequiredSubjectService;
import com.example.inspectionboard.service.SubjectService;
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
    private final SubjectService subjectService;
    private final FacultyService facultyService;
    private final FacultyRegistrationService facultyRegistrationService;
    private final RequiredSubjectService requiredSubjectService;

    @GetMapping()
    public String mainPage(Model model,
                           Authentication authentication,
                           @RequestParam(defaultValue = NAME_ASC) String facultyOrder) {
        model.addAttribute(USER_ROLE, getRole(authentication));
        model.addAttribute(FACULTIES, facultyService.findAllOrderBy(facultyOrder));
        model.addAttribute(FACULTY_ORDER, facultyOrder);
        return "faculty/main";
    }

    @RequestMapping(value = "/{facultyName}/info")
    public String infoPage(Model model, Authentication authentication, @PathVariable String facultyName) {
        try {
            var faculty = facultyService.findFacultyByName(facultyName);
            model.addAttribute(USER_ROLE, getRole(authentication));
            model.addAttribute(FACULTY_INFO, faculty);
            model.addAttribute(NOT_TAKEN_SUBJECTS, subjectService.findNotTakenByFaculty(faculty));
        } catch (NoSuchFacultyException e) {
            e.printStackTrace();
        }
        return "faculty/info";
    }

    @RequestMapping("/{facultyName}/register")
    public String register(HttpServletRequest request, Authentication authentication, @PathVariable String facultyName) {
        String status = SUCCESSFULLY;
        try {
            facultyRegistrationService.register(authentication.getName(), facultyName);
        } catch (NoSuchEnrolleeException | NoSuchFacultyException | CannotRegisterToFacultyException | AlreadyRegisteredException e) {
            status = e.getClass().getName();
        }
        request.getSession().setAttribute(FACULTY_REGISTRATION_STATUS, status);
        return ("redirect:/faculties/" + facultyName + "/info");
    }

    @PostMapping("/{facultyName}/createSubject")
    public String createSubject(HttpServletRequest request,
                                @PathVariable String facultyName,
                                @RequestParam String subjectName,
                                @RequestParam int minimalGrade) {
        String createSubjectResult = SUCCESSFULLY;
        try {
            requiredSubjectService.save(subjectName, facultyName, minimalGrade);
        } catch (NoSuchSubjectException | MarkIsNotValidException | NotUniqueSubjectException | NoSuchFacultyException e) {
            createSubjectResult = e.getClass().getSimpleName();
        }
        request.getSession().setAttribute(CREATE_SUBJECT_RESULT, createSubjectResult);
        return ("redirect:/faculties/" + facultyName + "/info");
    }

    @PostMapping("/{facultyName}/delete")
    public String deleteSubject(@PathVariable String facultyName) {
        facultyService.delete(facultyName);
        return "redirect:/faculties";
    }

    @PostMapping("/{facultyName}/changeRegistrationStatus")
    public String changeRegistrationStatus(HttpServletRequest request,
                                           @PathVariable String facultyName,
                                           @RequestParam String enrolleeLogin,
                                           @RequestParam FacultyRegistrationStatus newStatus) {
        String changeFacultyRegistrationResult = SUCCESSFULLY;
        try {
            facultyRegistrationService.changeStatus(newStatus, enrolleeLogin, facultyName);
        } catch (NoSuchFacultyException | NoSuchEnrolleeException | NotEnoughPlacesException e) {
            changeFacultyRegistrationResult = e.getClass().getSimpleName();
        }
        request.getSession().setAttribute(CHANGE_FACULTY_REGISTRATION_RESULT, changeFacultyRegistrationResult);
        return "redirect:/admin/main";
    }


    private static GrantedAuthority getRole(Authentication authentication) {
        if (authentication == null) {
            return AccountType.UNKNOWN;
        }
        return new ArrayList<>(authentication.getAuthorities()).get(0);
    }
}
