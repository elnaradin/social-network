package ru.itgroup.intouch.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Comment;
import model.enums.CommentType;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.dto.CommentDto;
import ru.itgroup.intouch.dto.SubCommentDto;

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
@Slf4j
public class SubсommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final CommentService commentService;


    public CommentDto createSubсommentToComment(Long commentId, SubCommentDto subCommentDto, Long userId) {

        Optional<Comment> commentEntity = commentRepository.findById(commentId);
        if (commentEntity.isEmpty()) {
            return null;
        }

        Comment comment = commentEntity.get();
        if (comment.isDeleted()) {
            return null;
        }

        Comment subcomment = new Comment();
        subcomment.setDeleted(false);
        subcomment.setCommentType(CommentType.COMMENT);
        subcomment.setTime(LocalDateTime.now());
        subcomment.setTimeChanged(LocalDateTime.now());
        subcomment.setAuthorId(userId);
        subcomment.setParentId(comment.getId());
        subcomment.setCommentText(subCommentDto.getSubComment());
        subcomment.setPostId(comment.getPostId());
        subcomment.setBlocked(comment.isBlocked());
        subcomment.setLikeAmount(0);
        subcomment.setMyLike(true);
        subcomment.setCommentsCount(0);
        subcomment.setImagePath("");

        commentRepository.save(subcomment);

        /* увеличить количество субкомментов в комменте */
        commentService.changeCommentCountOrLikeAmount(commentId, Operator.PLUS, Item.COUNT_COMMENTS);

        return modelMapper.map(subcomment, CommentDto.class);

    }

    public List<CommentDto> getSubcommentsByIdComment(Long idComment, Pageable pageable) {

        List<Comment> listSubcomments = commentRepository.findALLByParentId(idComment, pageable);

        if (listSubcomments.isEmpty()) {
            return new ArrayList<>();
        }

        return listSubcomments.stream().map(item -> modelMapper.map(item, CommentDto.class)).collect(Collectors.toList());

    }


}
