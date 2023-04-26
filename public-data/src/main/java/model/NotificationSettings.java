package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.account.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification_settings")
public class NotificationSettings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private boolean post = true;

    @Column(name = "post_comment")
    private boolean postComment = true;

    @Column(name = "comment_comment")
    private boolean commentComment = true;

    private boolean message = true;

    @Column(name = "friend_request")
    private boolean friendRequest = true;

    @Column(name = "friend_birthday")
    private boolean friendBirthday = true;

    @Column(name = "send_email_message")
    private boolean sendEmailMessage = true;
}
