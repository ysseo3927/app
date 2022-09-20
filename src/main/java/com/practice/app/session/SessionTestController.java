package com.practice.app.session;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class SessionTestController {

    @GetMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public void login(HttpServletRequest request) {

    }
}
