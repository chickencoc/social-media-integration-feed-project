package com.example.socialmediafeed.domain.hashtag.controller;

import com.example.socialmediafeed.domain.hashtag.dto.HotHashtagResDto;
import com.example.socialmediafeed.domain.hashtag.service.HotHashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/hashtags")
@RestController
public class HashtagController {

    private final HotHashtagService hotHashtagService;

    @GetMapping("/hot")
    public ResponseEntity<HotHashtagResDto> getHotHashtag() {
        return ResponseEntity.status(HttpStatus.OK).body(hotHashtagService.getHotHashtag());
    }

}
