package com.example.inspectionboard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static com.example.inspectionboard.model.enums.AccountType.ADMIN;
import static com.example.inspectionboard.model.enums.AccountType.ENROLLEE;

@Configuration
@EnableWebSecurity
public class AccountConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/enrollee/**").hasRole(ENROLLEE.name())
                .antMatchers("/enrollee/createSubject/**").hasRole(ENROLLEE.name())
                .antMatchers("/faculties/*/register/**").hasRole(ENROLLEE.name())

                .antMatchers("/admin/**").hasRole(ADMIN.name())
                .antMatchers("/faculties/*/delete/**").hasRole(ADMIN.name())
                .antMatchers("/faculties/*/changeRegistrationStatus/**").hasRole(ADMIN.name())
                .antMatchers("/faculties/*/create/**").hasRole(ADMIN.name())
                .antMatchers("/faculties/*/modify/**").hasRole(ADMIN.name())
                .antMatchers("/faculties/*/createSubject/**").hasRole(ADMIN.name())

                .antMatchers("/faculties/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().permitAll()
                .and()
                .logout().logoutSuccessUrl("/login").invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .and().csrf().disable();
    }
}


