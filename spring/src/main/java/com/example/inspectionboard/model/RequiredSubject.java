package com.example.inspectionboard.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "required_subject",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_subject_faculty",
                        columnNames = {"subject_id", "faculty_id"})})

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RequiredSubject{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Min(1)
    @Max(12)
    @NotNull
    private Integer requiredGrade;

    @ManyToOne
    @JoinColumn(name = "faculty_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Faculty faculty;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Subject subject;
}
