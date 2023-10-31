package com.example.socialmediafeed.domain.statistics.controller;

import com.example.socialmediafeed.domain.statistics.dto.StatisticsResponseDto;
import com.example.socialmediafeed.domain.statistics.service.StatisticsService;
import com.example.socialmediafeed.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/// 통계 관련 API
@Tag(name = "StatisticsController", description = "통계 API")
@RestController
@RequestMapping("/statistics")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final UserService userService;

    /**
     * 통계 반환 메서드
     *
     * @param hashtag
     * @param type
     * @param start
     * @param end
     * @param value
     * @return List<StatisticsResponseDto>
     */
    @Operation(summary = "SNS 게시글 통계 조회", description = "start ~ end 기간내 (시작일, 종료일 포함) 해당 hashtag가 포함된 게시물 수를 일자별로 제공합니다. " +
            "또는, start ~ end 기간내 (시작일, 종료일 포함) 해당 hashtag가 포함된 게시물 수를 시간별로 제공합니다. ")
    @GetMapping
    public ResponseEntity<List<StatisticsResponseDto>> getStatistics(
            @RequestHeader String Authorization,
            @RequestParam(required = false) String hashtag,
            @RequestParam String type, // date, hour 둘 중 하나
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end,
            @RequestParam(required = false) String value) {

        hashtag = hashtag == null ? userService.findUserByAccessToken(Authorization).getHashtag().getName() : hashtag;
        return ResponseEntity.status(HttpStatus.OK).body(statisticsService.getStatistics(hashtag, start, end, type, value));
    }
}
