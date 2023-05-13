package model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import model.account.Account;
import model.enums.Status;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "friends")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id_from", columnDefinition = "BIGINT", nullable = false)
    Account userIdFrom;

    @ManyToOne
    @JoinColumn(name = "user_id_to", columnDefinition = "BIGINT", nullable = false)
    Account userIdTo;

    @Column(name = "status_code", columnDefinition = "VARCHAR(255)", nullable = false)
    String statusCode;

    public Status getEnumStatusCode() {
        return Status.valueOf(statusCode);
    }

    public void setEnumStatusCode(Status status) {
        statusCode = status.getStatus();
    }

    public static class FriendBuilder {
        public FriendBuilder statusCodeE(Status statusCode) {
            this.statusCode = statusCode.getStatus();
            return this;
        }
    }
}
