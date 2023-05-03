package ru.itgroup.intouch.controller;
import dto.AccountSearchDto;
import dto.PostSearchDto;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import model.Account;
import model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.response.FalsePostResponse;
import ru.itgroup.intouch.response.PostResponse;
import ru.itgroup.intouch.response.TruePostResponse;
import ru.itgroup.intouch.service.PostSearchService;
import ru.itgroup.intouch.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostSearchService postSearchService;
    @GetMapping("/api/v1/post/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostDto postDto = postService.getPostById(id);
        if (postDto != null) {
            return new ResponseEntity<>(new TruePostResponse(true, "Пост найден", postDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(new FalsePostResponse(false, "Пост не найден"), HttpStatus.NOT_FOUND);
    }
    @PostMapping("/api/v1/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        PostDto post = postService.createNewPost(postDto);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(new FalsePostResponse(false, "Пост не создан"), HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/api/v1/post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {
        if (postService.deletePostById(id)) {
            return new ResponseEntity<>("Пост удален", HttpStatus.OK);
        }
        return new ResponseEntity<>("Посты не найден", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<FalsePostResponse> handleMissingServletParameterException(Exception exception) {
        return new ResponseEntity<>(new FalsePostResponse(false, exception.getLocalizedMessage()), HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/api/v1/post")
    public ResponseEntity<List<Post>> search(PostSearchDto postSearchDto, Pageable pageable) {
        List<Post> postList = postSearchService.getAccountResponse(postSearchDto, pageable);
        if (postList.isEmpty()) { return new ResponseEntity<>(HttpStatus.BAD_REQUEST); }
        return new ResponseEntity<List<Post>>(postList, HttpStatus.OK);
    }

}
