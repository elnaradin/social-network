package ru.itgroup.intouch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import model.enums.PostType;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;


@Data
public class PostDto {
    private Integer id;
    private boolean isDeleted;
    private ZonedDateTime publishDate;
    private ZonedDateTime time;
    private ZonedDateTime timeChanged;
    private Long authorId;
    private String title;
    @JsonProperty(value = "type")
    private String postType;
    private String postText;
    private boolean isBlocked;
    private Integer commentsCount;
    private List<String> PostTags;
    private Integer likeAmount;
    private boolean myLike;
    private String imagePath;

}