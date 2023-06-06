package ru.itgroup.intouch.dto.dialog;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ConversationPartner {
    public int id;
    public boolean isDeleted;
    public String firstName;
    public String lastName;
    public String email;
    public String password= "******";
    public String phone;
    public String photo;
    public String about;
    public String city;
    public String country;
    public String statusCode;
    public Long regDate;
    public Long birthDate;
    public String messagePermission;
    public Long lastOnlineTime;
    public boolean isOnline;
    public boolean isBlocked;
    public String photoId;
    public String photoName;
    public Long createdOn;
    public Long updatedOn;
}
