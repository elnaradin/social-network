package ru.itgroup.intouch.dto.parents;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public abstract class ResponseDTO {

    private String error;
    private String errorDescription;
    private Long timestamp = System.currentTimeMillis();
}
