package ru.itgroup.intouch.controller;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.response.FalsePostResponse;
import ru.itgroup.intouch.response.PostResponse;
import ru.itgroup.intouch.response.TruePostResponse;
import ru.itgroup.intouch.service.PostService;

@RestController
@RequestMapping("/api/v1/post")
@AllArgsConstructor
public class PostController {
    private final PostService postService;
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        PostDto postDto = postService.getPostById(id);
        if (postDto != null) {
            return new ResponseEntity<>(new TruePostResponse(true, "Пост найден", postDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(new FalsePostResponse(false, "Пост не найден"), HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto) {
        PostDto post = postService.createNewPost(postDto);
        if (post != null) {
            return new ResponseEntity<>(post, HttpStatus.OK);
        }
        return new ResponseEntity<>(new FalsePostResponse(false, "Пост не создан"), HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
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

}
