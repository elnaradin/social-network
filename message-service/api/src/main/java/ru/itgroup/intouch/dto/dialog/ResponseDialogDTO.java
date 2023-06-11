package ru.itgroup.intouch.dto.dialog;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itgroup.intouch.dto.parents.PageableResponseDTO;

import java.util.List;

@Data
@NoArgsConstructor
public class ResponseDialogDTO extends PageableResponseDTO {

    private Integer currentUserId;
    private List<DialogDTO> data;
}
