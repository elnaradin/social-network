package ru.itgroup.intouch.dto.parents;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageableResponseDTO extends ResponseDTO {
    private Integer total;
    private Integer offset;
    private Integer perPage;
}
