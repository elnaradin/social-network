package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import model.enums.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.config.NotificationProperties;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.service.CommentService;
import ru.itgroup.intouch.service.NotificationKafkaProducer;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class CommentController {
    private final NotificationKafkaProducer producer;
    private final CommentService commentService;
    @Autowired
    NotificationProperties notificationProperties;

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> createCommentToPost(@PathVariable(value = "id") Long idPost, @RequestBody CommentDto dto) {
        CommentDto comment = commentService.createNewComment(dto, idPost);
        producer.produce(comment.getAuthorId(), comment.getId(), NotificationType.POST_COMMENT);

        return (comment.getPostId() != null) ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/comment")
    public ResponseEntity<?> getCommentsToPost(@PathVariable(value = "id") Long idPost,
                                               @SpringQueryMap Pageable pageable) {
        List<CommentDto> commentDtoList = commentService.getCommentsByIdPost(idPost, pageable);

        return (!commentDtoList.isEmpty()) ? ResponseEntity.ok(commentDtoList) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Long commentId) {
        return (commentService.deleteComment(commentId))
               ? ResponseEntity.ok("Пост удален")
               : ResponseEntity.notFound().build();
    }
}
