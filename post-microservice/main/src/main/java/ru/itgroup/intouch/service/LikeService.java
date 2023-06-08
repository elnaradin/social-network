package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.Likes;
import model.enums.LikeType;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.LikeDto;

import ru.itgroup.intouch.repository.LikeRepository;

import ru.itgroup.intouch.service.enums.Item;
import ru.itgroup.intouch.service.enums.Operator;

import java.time.LocalDateTime;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class LikeService {


    private final LikeRepository likeRepository;
    private final PostService postService;
    private final ModelMapper modelMapper;
    private final CommentService commentService;

    public LikeDto createLike(Long id, LikeType likeType) {

        Likes like = new Likes();
        like.setDeleted(false);
        like.setAuthorId(7L);
        like.setTime(LocalDateTime.now());
        like.setItemId(id);
        like.setType(likeType);


        /* увеличить количество лайков в посте или комменте */
        if (likeType == LikeType.POST)
            postService.changeCommentCountOrLikeAmount(id, Operator.PLUS, Item.LIKE_AMOUNT);
        if (likeType == LikeType.COMMENT)
            commentService.changeCommentCountOrLikeAmount(id, Operator.PLUS, Item.LIKE_AMOUNT);

        Likes newLike = likeRepository.save(like);

        return modelMapper.map(newLike, LikeDto.class);
    }


    public boolean deleteLike(Long id, LikeType likeType) {
        Optional<Likes> likeEntity = Optional.ofNullable(likeRepository.findByItemId(id));

        if (likeEntity.isEmpty()) {
            return false;
        }

        Likes like = likeEntity.get();

        like.setDeleted(true);

        likeRepository.save(like);

        /* уменьшить количество лайков в посте или комменте*/
        if (likeType == LikeType.POST)
            postService.changeCommentCountOrLikeAmount(like.getItemId(), Operator.MINUS, Item.LIKE_AMOUNT);

        if (likeType == LikeType.COMMENT)
            commentService.changeCommentCountOrLikeAmount(like.getItemId(), Operator.MINUS, Item.LIKE_AMOUNT);


        return true;
    }


}
