package ru.itgroup.intouch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

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
    @JsonProperty(defaultValue = "0")
    private Integer commentsCount;
    private List<String> PostTags;
    @JsonProperty(defaultValue = "0")
    private Integer likeAmount;
    private boolean myLike;
    private String imagePath;

}