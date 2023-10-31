package com.example.socialmediafeed.domain.post.controller;

import com.example.socialmediafeed.domain.post.entity.Post;
import com.example.socialmediafeed.domain.post.service.PostInteractionService;
import com.example.socialmediafeed.domain.post.service.PostReadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "PostController", description = "게시글 API")
@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostReadService postReadService;
    private final PostInteractionService postInteractionService;

    @Operation(summary = "SNS 게시글 조회", description = "전체 SNS 게시글 전체 조회 기능입니다. 5개 단위로 페이지 네이션이 적용되어 있으며, 작성일 순으로 기본 정렬됩니다.")
    @GetMapping
    public ResponseEntity<Page<Post>> getPosts(
            @RequestParam(required = false) String hashtag,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int page_count,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder
    ) {
        Page<Post> postsList = postReadService.getPosts(hashtag, page, page_count, sortBy, sortOrder);

        return ResponseEntity.ok(postsList);
    }

    @Operation(summary = "SNS 게시글 상세조회", description = "SNS 게시글 상세 죄회 기능입니다.")
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = postReadService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @Operation(summary = "SNS 게시글 좋아요", description = "SNS 게시글 좋아요 클릭시 좋아요 횟수가 증가합니다.")
    @GetMapping("/{postId}/likes")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        postInteractionService.likePost(postId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "SNS 게시글 공유", description = "전체 SNS 게시글 공유 클릭시 공유 횟수가 증가합니다.")
    @GetMapping("/{postId}/shares")
    public ResponseEntity<?> sharePost(@PathVariable Long postId) {
        postInteractionService.sharePost(postId);
        return ResponseEntity.ok().build();
    }
}
