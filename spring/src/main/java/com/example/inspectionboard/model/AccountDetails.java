package com.example.inspectionboard.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Collections;

@Entity

@Getter
@EqualsAndHashCode
public class AccountDetails implements UserDetails {
    @Id
    @Column(name = "account_id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "account_id")
    private Account account;

    @NotBlank
    @Column(unique = true)
    private String login;

    @NotBlank
    @Column
    private String password;

    private boolean isBlocked;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(account.getAccountType());
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !isBlocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
