package com.example.inspectionboard.config;

import com.example.inspectionboard.service.AccountDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class AccountAuthentication implements AuthenticationProvider {
    private final AccountDetailsService service;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        var accountDetails = service.loadUserByUsername(authentication.getName());
        if (!accountDetails.getPassword().equals(authentication.getCredentials().toString())){
            throw new BadCredentialsException("Authentication failed");
        }
        return new UsernamePasswordAuthenticationToken(accountDetails.getUsername(), accountDetails.getPassword(), accountDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
