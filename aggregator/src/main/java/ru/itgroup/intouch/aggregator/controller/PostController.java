package ru.itgroup.intouch.aggregator.controller;

import dto.PostSearchDto;
import dto.PostSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.client.PostServiceClient;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.dto.SubCommentDto;


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

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> createCommentToPost(@PathVariable(value = "id") Long idPost, @RequestBody CommentDto dto) {
        return client.createCommentToPost(idPost, dto);
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<?> getCommentsToPost(@PathVariable(value = "id") Long idPost, @RequestBody Pageable pageable) {
        return client.getCommentsToPost(idPost, pageable);
    }

    @DeleteMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Long commentId) {
        return client.deleteComment(commentId);
    }

    @PutMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> createSubcommentToComment(@PathVariable(value = "commentId") Long commentId, @RequestBody SubCommentDto subComment) {
        return client.createSubcommentToComment(commentId, subComment);
    }

    @GetMapping("/{id}/comment/{commentId}/subcomment")
    public ResponseEntity<?> getSubcommentsToComment(@PathVariable(value = "commentId") Long commentId, @RequestBody Pageable pageable) {
        return client.getSubcommentsToComment(commentId, pageable);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> createLikeToPost(@PathVariable(value = "id") Long idPost) {

        return client.createLikeToPost(idPost);
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<?> deleteLikeToPost(@PathVariable(value = "id") Long idPost) {
        return client.deleteLikeToPost(idPost);
    }

    @PostMapping("/{id}/comment/{commentId}/like")
    public ResponseEntity<?> createLikeToComment(@PathVariable(value = "commentId") Long idComment) {
        return client.createLikeToComment(idComment);
    }

    @DeleteMapping("/{id}/comment/{commentId}/like")
    public ResponseEntity<?> deleteLikeToComment(@PathVariable(value = "commentId") Long idComment) {
        return client.deleteLikeToComment(idComment);
    }

}

