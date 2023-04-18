package ru.itgroup.intouch.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class ResponseDto {
    private LocalDateTime timeStamp = LocalDateTime.now();
}
