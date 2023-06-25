package ru.itgroup.intouch.controller;


import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.config.NotificationProperties;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.service.CommentService;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class CommentController {

    private final CommentService commentService;
    @Autowired
    NotificationProperties notificationProperties;

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> createCommentToPost(@PathVariable(value = "id") Long idPost, @RequestBody CommentDto dto) {
        CommentDto comment = commentService.createNewComment(dto, idPost);
        makeNotification(comment.getAuthorId(), Math.toIntExact(comment.getId()), "COMMENT");

        return (comment.getPostId() != null) ? ResponseEntity.ok(comment) : ResponseEntity.notFound().build();
    }


    @GetMapping("/{id}/comment")
    public ResponseEntity<?> getCommentsToPost(@PathVariable(value = "id") Long idPost, @SpringQueryMap Pageable pageable) {
        List<CommentDto> commentDtoList = commentService.getCommentsByIdPost(idPost, pageable);

        return (!commentDtoList.isEmpty()) ? ResponseEntity.ok(commentDtoList) : ResponseEntity.notFound().build();


    }

    @DeleteMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Long commentId) {

        return (commentService.deleteComment(commentId)) ? ResponseEntity.ok("Пост удален") : ResponseEntity.notFound().build();
    }

    public boolean makeNotification(Long authorId, Integer itemId, String type) {

        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpUriRequest request = RequestBuilder.post()
                    .setUri(notificationProperties.getUri())
                    .setHeader(HttpHeaders.CONTENT_TYPE, "application/json")
                    .setEntity(new StringEntity("{\"authorId\":" + authorId + ",\"itemId\":" + itemId + ",\"type\":" + type + "}"))
                    .build();
            HttpResponse response = httpclient.execute(request);

            //         System.out.println("Status code: " + response.getStatusLine().getStatusCode());
            return true;
        } catch (IOException e) {
            return false;
        }

    }

}
