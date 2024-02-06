package org.mizuro.springboot.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @PostMapping("/process_login")
    public String auth() {

    }
}
