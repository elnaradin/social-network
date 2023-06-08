package ru.itgroup.intouch.dto;

import lombok.Data;
import model.enums.LikeType;

import java.time.LocalDateTime;

@Data
public class LikeDto {


    private Long id;

    private boolean isDeleted;

    private int authorId;

    private LocalDateTime time;

    private int itemId;

    private LikeType type;
}

