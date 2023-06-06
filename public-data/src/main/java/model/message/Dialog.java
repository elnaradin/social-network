package model.message;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "dialogs")
@NoArgsConstructor
public class Dialog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "is_deleted")
    private boolean isDeleted;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "last_message_id", referencedColumnName = "id")
    private Message lastMessage;
    @OneToMany(mappedBy="dialog", cascade = CascadeType.PERSIST)
    private List<AccountDialog> accounts;

    public void addAccount(AccountDialog accountDialog){
        accounts.add(accountDialog);
    }
}
