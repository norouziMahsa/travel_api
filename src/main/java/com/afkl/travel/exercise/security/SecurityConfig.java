package com.afkl.travel.exercise.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${locations.base.path}")
    String locationsBasePath;

    @Value("${application.user}")
    String applicationUser;

    @Value("${application.password}")
    String applicationPassword;

    @Value("${actuator.user}")
    String actuatorUser;

    @Value("${actuator.password}")
    String actuatorPassword;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.inMemoryAuthentication()
                .withUser(applicationUser)
                .password(passwordEncoder.encode(applicationPassword))
                .roles("USER")
                .and()
                .withUser(actuatorUser)
                .password(passwordEncoder.encode(actuatorPassword))
                .roles("ADMIN");
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/h2-console/**")
                .permitAll()
                .and()
                .authorizeRequests()
                .antMatchers(locationsBasePath + "/**")
                .hasAnyRole("USER")
                .and()
                .authorizeRequests()
                .antMatchers("/actuator/**")
                .hasAnyRole("ADMIN")
                .and()
                .httpBasic();
    }

}


