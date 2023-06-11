package ru.itgroup.intouch.dto.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itgroup.intouch.dto.parents.ResponseDTO;

@Data
@NoArgsConstructor
public class UnreadMessageResponseDTO extends ResponseDTO {
    private Integer data;

}
