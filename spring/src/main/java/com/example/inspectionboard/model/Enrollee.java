package com.example.inspectionboard.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Enrollee extends Account{
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String city;

    @NotBlank
    private String region;

    @NotBlank
    private String schoolName;

    private byte[] certificateScan;

    @OneToMany(mappedBy = "enrollee", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<FacultyRegistration> facultyRegistrationSet = new HashSet<>();

    @OneToMany(mappedBy = "enrollee", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<EnrolleeSubject> enrolleeSubjectSet = new HashSet<>();
}
