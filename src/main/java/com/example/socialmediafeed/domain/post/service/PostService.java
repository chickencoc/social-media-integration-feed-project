package com.example.socialmediafeed.domain.post.service;

import com.example.socialmediafeed.domain.post.entity.Post;
import com.example.socialmediafeed.domain.post.entity.QPost;
import com.example.socialmediafeed.domain.post.repository.PostRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

import static com.example.socialmediafeed.domain.post.entity.QPost.post;

@RequiredArgsConstructor
@Service
public class PostService {

    private final EntityManager entityManager;
    private final PostRepository postRepository;

    @Transactional
    public Page<Post> getPosts(String hashtag, int page, int page_count, String sortBy, String sortOrder) {

        QPost qPost = post;
        BooleanBuilder builder = new BooleanBuilder();

        if (hashtag != null && !hashtag.isEmpty()) {
            builder.and(qPost.content.toLowerCase().containsIgnoreCase("#" + hashtag.toLowerCase()));
        }

        Sort.Direction direction = Sort.Direction.fromString(sortOrder);
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, page_count, sort);

        JPAQuery<Post> query = new JPAQuery<>(entityManager);

        query.from(qPost)
                .where(builder)
                .select(qPost);

        List<Post> results = query.fetch();

        int startIndex = (int) pageable.getOffset();
        int endIndex = Math.min((startIndex + pageable.getPageSize()), results.size());

        if (startIndex > results.size() || startIndex < 0) {
            // Handle the case where startIndex is out of bounds
            return new PageImpl<>(Collections.emptyList(), pageable, 0);
        }

        Page<Post> pageResults = new PageImpl<>(results.subList(startIndex, endIndex), pageable, results.size());

        return pageResults;
}
