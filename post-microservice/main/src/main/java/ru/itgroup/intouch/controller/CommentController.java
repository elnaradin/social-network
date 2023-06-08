package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.service.CommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/v1/post/{id}/comment")
    public ResponseEntity<?> createCommentToPost(@PathVariable(value = "id") Long idPost, @RequestBody CommentDto dto) {
        CommentDto comment = commentService.createNewComment(dto, idPost);

        return (comment.getPostId() != null) ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();
    }


    @GetMapping("/api/v1/post/{id}/comment")
    public ResponseEntity<?> getCommentsToPost(@PathVariable(value = "id") Long idPost, @RequestBody Pageable pageable) {
        List<CommentDto> commentDtoList = commentService.getCommentsByIdPost(idPost, pageable);

        return (!commentDtoList.isEmpty()) ? ResponseEntity.ok(commentDtoList) : ResponseEntity.notFound().build();


    }

    @DeleteMapping("/api/v1/post/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Long commentId) {

        return (commentService.deleteComment(commentId)) ? ResponseEntity.ok("Пост удален") : ResponseEntity.notFound().build();
    }

}
