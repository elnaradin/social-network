package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.dto.SubCommentDto;

import ru.itgroup.intouch.response.FalsePostResponse;
import ru.itgroup.intouch.service.SubсommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubсommentController {

    private final SubсommentService subCommentService;

    @PutMapping("/api/v1/post/{id}/comment/{commentId}")
    public ResponseEntity<?> createSubcommentToComment(@PathVariable(value = "commentId") Long commentId, @RequestBody SubCommentDto subComment) {

        CommentDto subcommentDto = subCommentService.createSubсommentToComment(commentId, subComment);

        return (subcommentDto != null) ? ResponseEntity.ok(subComment) : ResponseEntity.notFound().build();

    }

    @GetMapping("/api/v1/post/{id}/comment/{commentId}/subcomment")
    public ResponseEntity<?> getSubcommentsToComment(@PathVariable(value = "commentId") Long commentId, @RequestBody Pageable pageable) {

        List<CommentDto> listSubcomments = subCommentService.getSubcommentsByIdComment(commentId, pageable);

        return (!listSubcomments.isEmpty()) ? ResponseEntity.ok(listSubcomments) : ResponseEntity.notFound().build();

    }


}
