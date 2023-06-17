package ru.itgroup.intouch.dto;

import lombok.Data;
import model.enums.CommentType;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;


@Data
public class CommentDto {
    private Long id;
    private boolean isDeleted;
    private CommentType commentType;
    private ZonedDateTime time;
    private ZonedDateTime timeChanged;
    private Long authorId;
    private Long parentId;
    private String commentText;
    private Long postId;
    private boolean isBlocked;
    private Integer commentsCount;
    private Integer likeAmount;
    private boolean myLike;
    private String imagePath;
}
