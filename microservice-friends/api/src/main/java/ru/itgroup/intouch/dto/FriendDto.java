package ru.itgroup.intouch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FriendDto {
    long id;
    Boolean isDeleted;
    String photo;
    String statusCode;
    String firstName;
    String lastName;
    String city;
    String country;
    String birthDate;
    boolean isOnline;
    @JsonProperty("Аккаунт, от которого идет запрос")
    long accountFrom;
    @JsonProperty("Аккаунт, к которому идет запрос")
    long accountTo;
    @JsonProperty("Предыдущий статус")
    String previousStatusCode;
    @JsonProperty("Рейтинг")
    int rating;
}