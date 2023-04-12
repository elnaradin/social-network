package model;

import lombok.Data;
import model.Account;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Dialog {

    @Id
    private int id;

    private boolean isDeleted;

    private int unreadCount;

    private Account conversationPartner;

    private Message lastMessage;
}
