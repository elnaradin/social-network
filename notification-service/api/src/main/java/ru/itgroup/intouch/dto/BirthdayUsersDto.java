package ru.itgroup.intouch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirthdayUsersDto {
    private Long authorId;
    private Long receiverId;
    private String name;
    private String lastName;
}
