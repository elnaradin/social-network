package ru.itgroup.intouch.service.account;

import jakarta.mail.MessagingException;
import ru.itgroup.intouch.config.email.EmailContents;

public interface CredentialsRenewalService {
    void setNewPassword(String linkId, String password);
    void sendLetter(String to, EmailContents contents, boolean addLinkId) throws MessagingException;
}
