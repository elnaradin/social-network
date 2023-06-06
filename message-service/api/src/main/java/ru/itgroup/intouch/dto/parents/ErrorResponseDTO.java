package ru.itgroup.intouch.dto.parents;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorResponseDTO extends ResponseDTO{
    private String error;
    private String errorDescription;
}
