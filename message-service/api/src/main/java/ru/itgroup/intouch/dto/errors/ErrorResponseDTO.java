package ru.itgroup.intouch.dto.errors;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itgroup.intouch.dto.parents.ResponseDTO;

@Data
@NoArgsConstructor
public class ErrorResponseDTO extends ResponseDTO {
    private String error;
    private String errorDescription;
}
