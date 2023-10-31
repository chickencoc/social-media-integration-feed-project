package com.example.socialmediafeed.domain.jwtoken.controller;

import com.example.socialmediafeed.domain.jwtoken.dto.CreateAccessTokenReqDto;
import com.example.socialmediafeed.domain.jwtoken.dto.CreateAccessTokenResDto;
import com.example.socialmediafeed.domain.jwtoken.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Tag(name = "TokenApiController", description = "토큰 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/tokens")
public class TokenApiController {

    private final TokenService tokenService;

    @Operation(summary = "새로운 AccessToken 발급", description = "RefreshToken으로 새로운 AccessToken 발급합니다.")
    @PostMapping
    public ResponseEntity<CreateAccessTokenResDto> createNewAccessTokenUsingRefreshToken(
            @RequestBody CreateAccessTokenReqDto request) {
        String newAccessToken = tokenService.createNewAccessTokenUsingRefreshToken(request.getRefreshToken());

        return ResponseEntity.ok(CreateAccessTokenResDto.of(newAccessToken));
    }

    @Operation(summary = "ADMIN 권한 확인", description = "admin 권한을 가진 사용자만 접근할 수 있는 API입니다.")
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> requireAdminPermission(
            @AuthenticationPrincipal User user) {
        System.out.println(user.getUsername() + ", " + user.getAuthorities());
        return ResponseEntity.ok("adminPermission");
    }
}
