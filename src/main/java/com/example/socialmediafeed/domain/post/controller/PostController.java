package com.example.socialmediafeed.domain.post.controller;

import com.example.socialmediafeed.domain.post.entity.Post;
import com.example.socialmediafeed.domain.post.service.PostInteractionService;
import com.example.socialmediafeed.domain.post.service.PostReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostReadService postReadService;
    private final PostInteractionService postInteractionService;

    @GetMapping("")
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

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable Long postId) {
        Post post = postReadService.getPostById(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping("/{postId}/likes")
    public ResponseEntity<?> likePost(@PathVariable Long postId) {
        postInteractionService.likePost(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/shares")
    public ResponseEntity<?> sharePost(@PathVariable Long postId) {
        postInteractionService.sharePost(postId);
        return ResponseEntity.ok().build();
    }
}
