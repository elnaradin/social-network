package ru.itgroup.intouch.dto.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itgroup.intouch.dto.parents.PageableResponseDTO;

import java.util.List;

@NoArgsConstructor
@Data
public class ResponseDialogMessageDTO extends PageableResponseDTO {
    private List<DialogMessageDTO> data;
}
