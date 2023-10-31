package com.example.socialmediafeed.domain.user.controller;

import com.example.socialmediafeed.domain.jwtoken.dto.CreateAccessTokenResDto;
import com.example.socialmediafeed.domain.user.dto.ApprovalReqDto;
import com.example.socialmediafeed.domain.user.dto.SignInReqDto;
import com.example.socialmediafeed.domain.user.dto.SignupReqDto;
import com.example.socialmediafeed.domain.user.dto.SignupResDto;
import com.example.socialmediafeed.domain.user.service.UserApprovalService;
import com.example.socialmediafeed.domain.user.service.UserSignInService;
import com.example.socialmediafeed.domain.user.service.UserSignupService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "UserApiController", description = "사용자 기능 API")
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserApiController {

    private final UserSignupService userSignupService;
    private final UserSignInService userSignInService;
    private final UserApprovalService userApprovalService;

    @Operation(summary = "사용자 회원가입", description = "사용자의 입력 정보를 받아 신규 사용자를 등록합니다.")
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
    @Operation(summary = "사용자 로그인", description = "사용자의 입력 정보를 받아 등록 여부, 비밀번호 일치 여부 확인 후 AccessToken을 반환합니다.")
    @PostMapping("/sign-in")
    public ResponseEntity<CreateAccessTokenResDto> signIn(
            @RequestBody
            @Valid SignInReqDto signInReqDto) {
        return ResponseEntity.ok(userSignInService.signIn(signInReqDto));
    }

    @Operation(summary = "사용자 가입 인증", description = "사용자의 회원가입 후 발송된 이메일 인증 코드 확인 후 가입 승인 처리를 합니다.")
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