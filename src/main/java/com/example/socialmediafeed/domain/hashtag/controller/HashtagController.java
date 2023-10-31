package com.example.socialmediafeed.domain.hashtag.controller;

import com.example.socialmediafeed.domain.hashtag.dto.HotHashtagResDto;
import com.example.socialmediafeed.domain.hashtag.service.HotHashtagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "HashtagController", description = "해쉬태그 API")
@RequiredArgsConstructor
@RequestMapping("/hashtags")
@RestController
public class HashtagController {

    private final HotHashtagService hotHashtagService;

    @Operation(summary = "3시간 이내 인기 Hashtag 추천", description = "최근 3시간 동안 가장 많이 사용된 Tag 를 추천하는 기능입니다.")
    @GetMapping("/hot")
    public ResponseEntity<HotHashtagResDto> getHotHashtag() {
        return ResponseEntity.status(HttpStatus.OK).body(hotHashtagService.getHotHashtag());
    }

}
