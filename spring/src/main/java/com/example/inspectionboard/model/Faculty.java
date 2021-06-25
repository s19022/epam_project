package com.example.inspectionboard.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotBlank(message = "Faculty name is mandatory")
    @Size(min = 4, max = 255)
    private String name;

    @Min(0)
    private int budgetPlaces;

    @Min(0)
    private int allPlaces;

    @OneToMany(mappedBy = "faculty", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<RequiredSubject> requiredSubjectSet = new HashSet<>();
}
