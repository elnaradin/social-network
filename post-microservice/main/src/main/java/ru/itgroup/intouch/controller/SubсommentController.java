package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.dto.SubCommentDto;
import ru.itgroup.intouch.service.SubсommentService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class SubсommentController {

    private final SubсommentService subCommentService;

    @PutMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> createSubcommentToComment(@PathVariable(value = "commentId") Long commentId, @RequestBody SubCommentDto subComment, Long userId) {

        CommentDto subcommentDto = subCommentService.createSubсommentToComment(commentId, subComment, userId);

        return (subcommentDto != null) ? ResponseEntity.ok(subComment) : ResponseEntity.notFound().build();

    }

    @GetMapping("/{id}/comment/{commentId}/subcomment")
    public ResponseEntity<?> getSubcommentsToComment(@PathVariable(value = "commentId") Long commentId, @SpringQueryMap Pageable pageable) {

        List<CommentDto> listSubcomments = subCommentService.getSubcommentsByIdComment(commentId, pageable);

        return (!listSubcomments.isEmpty()) ? ResponseEntity.ok(listSubcomments) : ResponseEntity.notFound().build();

    }


}
