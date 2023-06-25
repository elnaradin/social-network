package ru.itgroup.intouch.controller;

import dto.PostSearchDtoPageable;
import lombok.RequiredArgsConstructor;
import model.enums.NotificationType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itgroup.intouch.config.NotificationProperties;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.service.NotificationKafkaProducer;
import ru.itgroup.intouch.service.PostSearchService;
import ru.itgroup.intouch.service.PostService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {
    private final PostService postService;
    private final PostSearchService postSearchService;
    private final NotificationKafkaProducer producer;
    @Autowired
    NotificationProperties notificationProperties;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPost(@PathVariable Long id) {
        PostDto postDto = postService.getPostById(id);

        return (postDto != null) ? ResponseEntity.ok(postDto) : ResponseEntity.notFound().build();

    }

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto, @RequestParam Long userId) {
        PostDto post = postService.createNewPost(postDto, userId);
        if (post != null) {
            producer.produce(post.getAuthorId(), post.getId().longValue(), NotificationType.POST);
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
}





