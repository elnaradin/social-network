package ru.itgroup.intouch.controller;


import dto.PostSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.config.NotificationProperties;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.service.PostSearchService;
import ru.itgroup.intouch.service.PostService;

import java.io.IOException;
import java.io.UnsupportedEncodingException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")

public class PostController {
    private final PostService postService;
    private final PostSearchService postSearchService;
    @Autowired
    NotificationProperties notificationProperties;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        PostDto postDto = postService.getPostById(id);

        return (postDto != null) ? ResponseEntity.ok(postDto) : ResponseEntity.notFound().build();

    }

    @PostMapping("")
    public ResponseEntity<?> createPost( @RequestBody PostDto postDto,
                                        @RequestParam Long userId) {

        PostDto post = postService.createNewPost(postDto, userId);

        if (post != null) {
            makeNotification(post.getAuthorId(), post.getId(), "POST");
        }

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
        } catch (IOException e) {return  false;
       }

    }
}





