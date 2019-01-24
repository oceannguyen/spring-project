package com.ocean.springproject.web.rest;

import com.ocean.springproject.domain.User;
import com.ocean.springproject.security.AuthoritiesConstants;
import com.ocean.springproject.security.jwt.TokenProvider;
import com.ocean.springproject.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class UserResource {

    private final UserService userService;

    private final TokenProvider tokenProvider;

    public UserResource(UserService userService, TokenProvider tokenProvider) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/users")
    @Secured(AuthoritiesConstants.ADMIN)
    public Page<User> findAll(Pageable pageable) {
        return this.userService.findAll(pageable);
    }

    @GetMapping("ping")
    public String ping(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        CsrfToken token = (CsrfToken) httpServletRequest.getAttribute("_csrf");
        // Spring Security will allow the Token to be included in this header name
        httpServletResponse.setHeader("X-CSRF-HEADER", token.getHeaderName());
        // Spring Security will allow the token to be included in this parameter name
        httpServletResponse.setHeader("X-CSRF-PARAM", token.getParameterName());
        // this is the value of the token to be included as either a header or an HTTP parameter
        httpServletResponse.setHeader("X-CSRF-TOKEN", token.getToken());
        return "OK";
    }
}
