package ru.itgroup.intouch.dto.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class ResponseDto {
    private LocalDateTime timeStamp = LocalDateTime.now();

    @JsonCreator
    public ResponseDto(@JsonProperty("timeStamp") LocalDateTime timestamp) {
        this.timeStamp = timestamp;
    }
}
