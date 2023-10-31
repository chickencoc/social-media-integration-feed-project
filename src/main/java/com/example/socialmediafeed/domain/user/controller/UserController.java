package com.example.socialmediafeed.domain.user.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Tag(name = "UserController", description = "사용자 이메일 인증 관련 API")
@RequiredArgsConstructor
@Controller
public class UserController {

    @Operation(summary = "사용자 이메일 인증 번호 발송 템플릿", description = "사용자 이메일 인증 번호 발송용 템플릿으로 리다이렉션합니다.")
    @GetMapping("/email")
    public String getMailTemplate() {
        return "mail/email";
    }
}
