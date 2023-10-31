package com.example.socialmediafeed.domain.jwtoken.controller;

import com.example.socialmediafeed.domain.jwtoken.dto.CreateAccessTokenReqDto;
import com.example.socialmediafeed.domain.jwtoken.dto.CreateAccessTokenResDto;
import com.example.socialmediafeed.domain.jwtoken.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tokens")
public class TokenApiController {

    private final TokenService tokenService;

    @PostMapping
    public ResponseEntity<CreateAccessTokenResDto> createNewAccessTokenUsingRefreshToken(
            @RequestBody CreateAccessTokenReqDto request) {
        String newAccessToken = tokenService.createNewAccessTokenUsingRefreshToken(request.getRefreshToken());

        return ResponseEntity.ok(CreateAccessTokenResDto.of(newAccessToken));
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> requireAdminPermission(@AuthenticationPrincipal User user) {
        System.out.println(user.getUsername() + ", " + user.getAuthorities());
        return ResponseEntity.ok("adminPermission");
    }
}
