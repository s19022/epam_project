package com.example.inspectionboard.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "enrollee_subject",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "unique_subject_enrollee",
                        columnNames = {"subject_id", "enrollee_id"})})

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class EnrolleeSubject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "enrollee_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Enrollee enrollee;

    @Min(1)
    @Max(12)
    @NotNull
    private Integer mark;
}
