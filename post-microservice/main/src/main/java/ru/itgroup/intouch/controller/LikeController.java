package ru.itgroup.intouch.controller;

import lombok.RequiredArgsConstructor;
import model.enums.LikeType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itgroup.intouch.dto.LikeDto;
import ru.itgroup.intouch.service.LikeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{id}/like")
    public ResponseEntity<?> createLikeToPost(@PathVariable(value = "id") Long idPost, Long userId) {

        LikeDto likeDto = likeService.createLike(idPost, LikeType.POST, userId);

        return (likeDto != null) ? ResponseEntity.ok(likeDto) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<?> deleteLikeToPost(@PathVariable(value = "id") Long idPost) {

        return (likeService.deleteLike(idPost, LikeType.POST)) ? ResponseEntity.ok("Лайк удален") : ResponseEntity.notFound().build();

    }

    @PostMapping("/{id}/comment/{commentId}/like")
    public ResponseEntity<?> createLikeToComment(@PathVariable(value = "commentId") Long idComment, Long userId) {

        LikeDto likeDto = likeService.createLike(idComment, LikeType.COMMENT, userId);

        return (likeDto != null) ? ResponseEntity.ok(likeDto) : ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}/comment/{commentId}/like")
    public ResponseEntity<?> deleteLikeToComment(@PathVariable(value = "commentId") Long idComment) {

        return (likeService.deleteLike(idComment, LikeType.COMMENT)) ? ResponseEntity.ok("Лайк удален") : ResponseEntity.notFound().build();

    }
}
