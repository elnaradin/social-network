package ru.itgroup.intouch.aggregator.controller;

import dto.PostSearchDtoPageable;
import feign.Param;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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

    @PostMapping("")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto,
                                        @Param("request") HttpServletRequest request) {
        Long userId = getAccountId(request);
        return client.createPost(postDto, userId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePost(@PathVariable Long id) {

        return client.deletePost(id);
    }

    @GetMapping("")
    public Page<PostDto> search(PostSearchDtoPageable postSearchDtoPageable,
                                @Param("request") HttpServletRequest request ) {
        Long userId = getAccountId(request);

        return client.search(postSearchDtoPageable, userId);
    }

    @PostMapping("/{id}/comment")
    public ResponseEntity<?> createCommentToPost(@PathVariable(value = "id") Long idPost,
                                                 @RequestBody CommentDto dto) {

        return client.createCommentToPost(idPost, dto);
    }

    @GetMapping("/{id}/comment")
    public ResponseEntity<?> getCommentsToPost(@PathVariable(value = "id") Long idPost,
                                               Pageable pageable) {
        return client.getCommentsToPost(idPost, pageable);
    }

    @DeleteMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Long commentId) {
        return client.deleteComment(commentId);
    }

    @PutMapping("/{id}/comment/{commentId}")
    public ResponseEntity<?> createSubcommentToComment(@PathVariable(value = "commentId") Long commentId,
                                                       @RequestBody SubCommentDto subComment,
                                                       @Param("request") HttpServletRequest request) {
        Long userId = getAccountId(request);
        return client.createSubcommentToComment(commentId, subComment, userId);
    }

    @GetMapping("/{id}/comment/{commentId}/subcomment")
    public ResponseEntity<?> getSubcommentsToComment(@PathVariable(value = "commentId") Long commentId,
                                                     Pageable pageable) {
        return client.getSubcommentsToComment(commentId, pageable);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<?> createLikeToPost(@PathVariable(value = "id") Long idPost,
                                              @Param("request")HttpServletRequest request) {
        Long userId = getAccountId(request);
        return client.createLikeToPost(idPost, userId);
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<?> deleteLikeToPost(@PathVariable(value = "id") Long idPost) {
        return client.deleteLikeToPost(idPost);
    }

    @PostMapping("/{id}/comment/{commentId}/like")
    public ResponseEntity<?> createLikeToComment(@PathVariable(value = "commentId") Long idComment,
                                                 @Param("request") HttpServletRequest request ) {
        Long userId = getAccountId(request);
        return client.createLikeToComment(idComment, userId);
    }

    @DeleteMapping("/{id}/comment/{commentId}/like")
    public ResponseEntity<?> deleteLikeToComment(@PathVariable(value = "commentId") Long idComment) {
        return client.deleteLikeToComment(idComment);
    }

    private Long getAccountId(HttpServletRequest request) {
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String token = header.split(" ")[1].trim();
        int i = token.lastIndexOf('.');
        String withoutSignature = token.substring(0, i+1);
        Jwt<Header, Claims> untrusted = Jwts.parser().parseClaimsJwt(withoutSignature);
        Object id = untrusted.getBody().get("userId");
        return Long.valueOf(id.toString());

    }
}

