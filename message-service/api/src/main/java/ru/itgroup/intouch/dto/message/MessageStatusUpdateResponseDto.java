package ru.itgroup.intouch.dto.message;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itgroup.intouch.dto.parents.ResponseDTO;
import ru.itgroup.intouch.dto.parents.ResponseMessage;

@Data
@NoArgsConstructor
public class MessageStatusUpdateResponseDto extends ResponseDTO {
    ResponseMessage data;
}
