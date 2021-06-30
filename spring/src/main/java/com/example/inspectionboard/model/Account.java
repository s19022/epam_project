package com.example.inspectionboard.model;

import com.example.inspectionboard.model.enums.AccountType;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    @Column(unique = true)
    private String login;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isBlocked;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, updatable = false)
    private AccountType accountType;
}
