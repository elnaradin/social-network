package model.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AccountDialogKey implements Serializable {
    private Long accountId;
    private Long recipientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDialogKey)) return false;
        AccountDialogKey that = (AccountDialogKey) o;
        return accountId.equals(that.accountId) && recipientId.equals(that.recipientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, recipientId);
    }
}
