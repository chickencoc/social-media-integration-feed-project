package com.example.socialmediafeed.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class UserController {
    @GetMapping("/email")
    public String getMailTemplate() {
        return "mail/email";
    }
}
