package com.ocean.springproject.config;

import com.ocean.springproject.security.jwt.JWTConfigurer;
import com.ocean.springproject.security.jwt.JWTFilter;
import com.ocean.springproject.security.jwt.TokenProvider;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final UserDetailsService userDetailsService;

    private final TokenProvider tokenProvider;

    public SecurityConfiguration(AuthenticationManagerBuilder authenticationManagerBuilder, UserDetailsService userDetailsService, TokenProvider tokenProvider) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.tokenProvider = tokenProvider;
    }

    @PostConstruct
    public void init() {
        try {
            authenticationManagerBuilder
                    .userDetailsService(this.userDetailsService)
                    .passwordEncoder(this.passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .csrf()
                .disable()
                .headers()
                .frameOptions()
                .disable()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .authorizeRequests()
                .antMatchers("/api/authenticate").permitAll()
                .anyRequest().authenticated()
            .and()
                .apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

    private JWTFilter jwtFilter() {
        return new JWTFilter(tokenProvider);
    }
}
