package com.ocean.springproject.web.rest;

import com.ocean.springproject.domain.User;
import com.ocean.springproject.security.AuthoritiesConstants;
import com.ocean.springproject.security.jwt.TokenProvider;
import com.ocean.springproject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    public UserResource(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public Page<User> findAll(Pageable pageable) {
        return this.userService.findAll(pageable);
    }
}
