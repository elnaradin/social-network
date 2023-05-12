package ru.itgroup.intouch.dto.response.notifications;

import com.googlecode.jmapper.annotations.JMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    @JMap
    private Long id;

    @JMap
    private String firstName;

    @JMap
    private String lastName;

    @JMap
    private String photo;
}
