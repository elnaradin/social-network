package model.message;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "account_dialogs")
@NoArgsConstructor
@AllArgsConstructor
@Data
@IdClass(AccountDialogKey.class)
public class AccountDialog {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dialog_id")
    private Dialog dialog;
    @Id
    @Column(name = "account_id")
    private Long accountId;
    @Id
    @Column(name = "recipient_id")
    private Long recipientId;

    public Long getDialogId() {
        return dialog.getId();
    }

    public boolean getDialogIsDeleted() {
        return dialog.isDeleted();
    }

    public Message getDialogLastMessage() {
        return dialog.getLastMessage();
    }
}
