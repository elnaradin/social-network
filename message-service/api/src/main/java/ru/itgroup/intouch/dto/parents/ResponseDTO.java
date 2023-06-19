package ru.itgroup.intouch.dto.parents;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public abstract class ResponseDTO {

    private Long timestamp = System.currentTimeMillis();
}
