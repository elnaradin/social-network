package ru.itgroup.intouch.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendSearchDto {
    Long id;
    Boolean isDeleted;
    List<Long> ids;
    Long idFrom;
    String statusCode;
    Long idTo;
    String firstName;
    String birthDateFrom;
    String city;
    String country;
    Long ageFrom;
    Long ageTo;
    String previousStatusCode;
}
