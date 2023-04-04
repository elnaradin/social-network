package ru.itgroup.intouch.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PostDto {
    private Integer id;
    private boolean isDeleted;
    private LocalDateTime publishDate;
    private LocalDateTime time;
    private LocalDateTime timeChanged;
    private Integer authorId;
    private String title;
    private String type;
    private String postText;
    private boolean isBlocked;
    private Integer commentsCounts;
    private List<String> tags;
    private Integer likeAmount;
    private boolean myLike;

}