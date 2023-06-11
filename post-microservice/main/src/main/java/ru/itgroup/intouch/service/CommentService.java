package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import model.Comment;

import model.enums.CommentType;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.CommentDto;

import ru.itgroup.intouch.repository.CommentRepository;

import ru.itgroup.intouch.service.enums.Item;
import ru.itgroup.intouch.service.enums.Operator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final PostService postService;


    public CommentDto createNewComment(CommentDto commentDto, Long idPost) {


        Comment comment = new Comment();

        comment.setDeleted(false);
        comment.setCommentType(commentDto.getCommentType());
        comment.setTime(LocalDateTime.now());
        comment.setTimeChanged(LocalDateTime.now());
        comment.setAuthorId(commentDto.getAuthorId());
        comment.setParentId(commentDto.getParentId());
        comment.setCommentText(commentDto.getCommentText());
        comment.setPostId(idPost);
        comment.setBlocked(false);
        comment.setLikeAmount(commentDto.getLikeAmount());
        comment.setMyLike(commentDto.isMyLike());
        comment.setCommentsCount(commentDto.getCommentsCount());
        comment.setImagePath(commentDto.getImagePath());
        commentRepository.save(comment);
        /* увеличить количество комментов в посте*/
        postService.changeCommentCountOrLikeAmount(idPost, Operator.PLUS, Item.COUNT_COMMENTS);

        return modelMapper.map(comment, CommentDto.class);
    }

    public List<CommentDto> getCommentsByIdPost(Long idPost, Pageable pageable) {

        List<Comment> listComments = commentRepository.findAllByPostId(idPost, pageable);

        if (listComments.isEmpty()) {
            return new ArrayList<>();
        }

        return listComments.stream().map(item -> modelMapper.map(item, CommentDto.class)).collect(Collectors.toList());

    }

    public boolean deleteComment(Long commentId) {

        Optional<Comment> commentEntity = commentRepository.findById(commentId);

        if (commentEntity.isEmpty()) {
            return false;
        }

        Comment comment = commentEntity.get();

        comment.setDeleted(true);

        commentRepository.save(comment);

        /* уменьшить количество комментов в самом посте */
        postService.changeCommentCountOrLikeAmount(comment.getPostId(), Operator.MINUS, Item.COUNT_COMMENTS);


        return true;
    }

    public void changeCommentCountOrLikeAmount(Long id, Operator operator, Item item) {

        Optional<Comment> commentEntity = commentRepository.findById(id);

        if (commentEntity.isPresent()) {

            Comment comment = commentEntity.get();

            /*изменение количества комментов */
            if (item == Item.COUNT_COMMENTS) {

                int countComments = (operator == Operator.PLUS) ? comment.getCommentsCount() + 1 : comment.getCommentsCount() - 1;
                comment.setCommentsCount(countComments);
            }
            /*изменение количества лайков*/
            if (item == Item.LIKE_AMOUNT) {

                int likeAmount = (operator == Operator.PLUS) ? comment.getLikeAmount() + 1 : comment.getLikeAmount() - 1;
                comment.setLikeAmount(likeAmount);
            }

            commentRepository.save(comment);
        }
    }


}