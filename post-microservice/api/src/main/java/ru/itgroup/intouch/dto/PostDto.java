package ru.itgroup.intouch.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class PostDto {
    private Integer id;
    private boolean isDeleted;
    private LocalDateTime publishDate;
    private LocalDateTime time;
    private LocalDateTime timeChanged;
    private Integer authorId;
    private String title;
    private String postType;
    private String postText;
    private boolean isBlocked;
    private Integer commentsCount;
    private List<String> PostTags;
    private Integer likeAmount;
    private boolean myLike;
    private String imagePath;

}