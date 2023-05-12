package ru.itgroup.intouch.aggregator.controller;

import dto.PostSearchDto;
import dto.PostSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.client.PostServiceClient;
import ru.itgroup.intouch.dto.PostDto;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostServiceClient client;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        return client.getPost(id);

    }

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        return client.createPost(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        return client.deletePost(id);
    }

    @GetMapping("")
    public ResponseEntity<?> search(PostSearchDtoPageable dtoPageable) {
        return client.search(dtoPageable);
    }

}

