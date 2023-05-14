package ru.itgroup.intouch.client;

import dto.PostSearchDto;
import dto.PostSearchDtoPageable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.PostDto;

@FeignClient(name = "post-service", url = "${POST_HOST}:${POST_PORT}", path = "/api/v1/post")
public interface PostServiceClient {


    @GetMapping("/{id}")
    ResponseEntity<?> getPost(@PathVariable Long id);

    @PostMapping("")
    ResponseEntity<?> createPost(PostDto postDto);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePost(@PathVariable Long id);

    @GetMapping("/api/v1/post")
    public ResponseEntity<?> search(PostSearchDtoPageable dtoPageable);
}


