package ru.itgroup.intouch.client;

import dto.PostSearchDtoPageable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.dto.PostDto;
import ru.itgroup.intouch.dto.SubCommentDto;


@FeignClient(name = "post-service", url = "${SN_POST_HOST}:${SN_POST_PORT}", path = "/api/v1/post")
public interface PostServiceClient {


    @GetMapping("/{id}")
    ResponseEntity<?> getPost(@PathVariable Long id);

    @PostMapping("")
    ResponseEntity<?> createPost(@RequestBody PostDto postDto,
                                 @RequestParam Long userId);

    @DeleteMapping("/{id}")
    ResponseEntity<?> deletePost(@PathVariable Long id);

    @GetMapping("")
    ResponseEntity<?> search(@SpringQueryMap PostSearchDtoPageable dtoPageable,
                             @RequestParam Long userId);

    @PostMapping("/{id}/comment")
    ResponseEntity<?> createCommentToPost(@PathVariable(value = "id") Long idPost,
                                          @RequestBody CommentDto dto);


    @GetMapping("/{id}/comment")
    ResponseEntity<?> getCommentsToPost(@PathVariable(value = "id") Long idPost,
                                        @SpringQueryMap Pageable pageable);

    @DeleteMapping("/{id}/comment/{commentId}")
    ResponseEntity<?> deleteComment(@PathVariable(value = "commentId") Long commentId);

    @PutMapping("/{id}/comment/{commentId}")
    ResponseEntity<?> createSubcommentToComment(@PathVariable(value = "commentId") Long commentId,
                                                @RequestBody SubCommentDto subComment,
                                                @RequestParam Long userId);

    @GetMapping("/{id}/comment/{commentId}/subcomment")
    ResponseEntity<?> getSubcommentsToComment(@PathVariable(value = "commentId") Long commentId,
                                              @SpringQueryMap Pageable pageable);

    @PostMapping("/{id}/like")
    ResponseEntity<?> createLikeToPost(@PathVariable(value = "id") Long idPost,
                                       @RequestParam Long userId);

    @DeleteMapping("/{id}/like")
    ResponseEntity<?> deleteLikeToPost(@PathVariable(value = "id") Long idPost);

    @PostMapping("/{id}/comment/{commentId}/like")
    ResponseEntity<?> createLikeToComment(@PathVariable(value = "commentId") Long idComment,
                                          @RequestParam Long userId);

    @DeleteMapping("/{id}/comment/{commentId}/like")
    ResponseEntity<?> deleteLikeToComment(@PathVariable(value = "commentId") Long idComment);

}


