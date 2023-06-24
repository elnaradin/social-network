package ru.itgroup.intouch.controller;


import dto.PostSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.service.PostSearchService;
import ru.itgroup.intouch.service.PostService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    private final PostSearchService postSearchService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        PostDto postDto = postService.getPostById(id);

        return (postDto != null) ? ResponseEntity.ok(postDto) : ResponseEntity.notFound().build();

    }

    @PostMapping("")
    public ResponseEntity<?> createPost( @RequestBody PostDto postDto,
                                        @RequestParam Long userId) {

        PostDto post = postService.createNewPost(postDto, userId);

        return (post != null) ? ResponseEntity.ok(post) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        return (postService.deletePostById(id)) ? ResponseEntity.ok("Пост удален") : ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<?> handleMissingServletParameterException(Exception exception) {

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("")
    public Page<PostDto> search(@SpringQueryMap PostSearchDtoPageable dto, @RequestParam Long userId) {

                return postSearchService.getPostResponse(dto, userId);
    }



}





