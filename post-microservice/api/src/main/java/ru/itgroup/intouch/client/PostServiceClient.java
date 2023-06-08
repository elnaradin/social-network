package ru.itgroup.intouch.client;

import dto.PostSearchDtoPageable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.dto.SubCommentDto;

@FeignClient(name = "post-service", url = "${SN_POST_HOST}:${SN_POST_PORT}", path = "/api/v1/post")
public interface PostServiceClient {


    @GetMapping("/{id}")
    ResponseEntity<?> getPost(@PathVariable Long id);

    @PostMapping("")
    ResponseEntity<?> createPost(@RequestBody PostDto postDto);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePost(@PathVariable Long id);

    @GetMapping("")
    public ResponseEntity<?> search(PostSearchDtoPageable dtoPageable);

    @PostMapping("/api/v1/post/{id}/comment")
    public ResponseEntity<?> createCommentToPost(@PathVariable(value = "id") Long idPost, @RequestBody CommentDto dto);

    @GetMapping("/api/v1/post/{id}/comment")
    public ResponseEntity<?> getCommentsToPost(@PathVariable(value = "id") Long idPost, @RequestBody Pageable pageable);

    @DeleteMapping("/api/v1/post/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Long commentId);

    @PutMapping("/api/v1/post/{id}/comment/{commentId}")
    public ResponseEntity<?> createSubcommentToComment(@PathVariable(value = "commentId") Long commentId, @RequestBody SubCommentDto subComment);

    @GetMapping("/api/v1/post/{id}/comment/{commentId}/subcomment")
    public ResponseEntity<?> getSubcommentsToComment(@PathVariable(value = "commentId") Long commentId, @RequestBody Pageable pageable);

    @PostMapping("/api/v1/post/{id}/like")
    public ResponseEntity<?> createLikeToPost(@PathVariable(value = "id") Long idPost);

    @DeleteMapping("/api/v1/post/{id}/like")
    public ResponseEntity<?> deleteLikeToPost(@PathVariable(value = "id") Long idPost);

    @PostMapping("/api/v1/post/{id}/comment/{commentId}/like")
    public ResponseEntity<?> createLikeToComment(@PathVariable(value = "commentId") Long idComment);

    @DeleteMapping("/api/v1/post/{id}/comment/{commentId}/like")
    public ResponseEntity<?> deleteLikeToComment(@PathVariable(value = "commentId") Long idComment);
}


