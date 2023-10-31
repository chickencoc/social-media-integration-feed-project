package com.example.socialmediafeed.domain.user.controller;

import com.example.socialmediafeed.domain.jwtoken.dto.CreateAccessTokenResDto;
import com.example.socialmediafeed.domain.user.dto.ApprovalReqDto;
import com.example.socialmediafeed.domain.user.dto.SignInReqDto;
import com.example.socialmediafeed.domain.user.dto.SignupReqDto;
import com.example.socialmediafeed.domain.user.dto.SignupResDto;
import com.example.socialmediafeed.domain.user.service.UserApprovalService;
import com.example.socialmediafeed.domain.user.service.UserSignInService;
import com.example.socialmediafeed.domain.user.service.UserSignupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserApiController {

    private final UserSignupService userSignupService;
    private final UserSignInService userSignInService;
    private final UserApprovalService userApprovalService;


    @PostMapping("/sign-up")
    public ResponseEntity<SignupResDto> signUp(
            @RequestBody
            @Valid
            SignupReqDto signupReqDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userSignupService.signUp(signupReqDto));
    }

    /**
     * 로그인 성공시 access token 반환
     * @param signInReqDto username, password
     * @return accessToken
    * */
    @PostMapping("/sign-in")
    public ResponseEntity<CreateAccessTokenResDto> signIn(
            @RequestBody
            @Valid SignInReqDto signInReqDto) {
        return ResponseEntity.ok(userSignInService.signIn(signInReqDto));
    }

    @PatchMapping("/{id}/approval")
    public ResponseEntity<Object> approval(
            @PathVariable(name = "id")
            Long id,
            @RequestBody
            @Valid
            ApprovalReqDto approvalReqDto) {
        userApprovalService.approve(id, approvalReqDto);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}