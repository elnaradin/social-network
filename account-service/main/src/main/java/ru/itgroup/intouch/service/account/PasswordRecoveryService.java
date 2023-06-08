package ru.itgroup.intouch.service.account;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import model.account.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.exceptions.NoEmailFoundException;
import ru.itgroup.intouch.exceptions.NoUserRegisteredException;
import ru.itgroup.intouch.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PasswordRecoveryService {
    @Value("${email.message}")
    private String text;
    @Value("${email.subject}")
    private String subject;
    @Value("${EMAIL_FROM}")
    private String from;
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    @Value("${user.hash-expiry-min}")
    private long expiryMinutes;

    public void setNewPassword(String linkId, String password) {
        Optional<User> firstByEmail = userRepository.findFirstByHash(linkId);
        if (firstByEmail.isEmpty()) {
            throw new NoUserRegisteredException("Unable to change password. User with hash \"" +
                    linkId + "\" not found");
        }
        firstByEmail.get().setPassword(password);
        userRepository.save(firstByEmail.get());
    }

    public void sendLetter(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        Optional<User> optUser = userRepository.findFirstByEmail(to);
        if (optUser.isEmpty()) {
            throw new NoEmailFoundException("Пользователь с адресом \"" + to + "\" не зарегистрирован");
        }
        User user = optUser.get();
        String userHash = UUID.randomUUID().toString();
        message.setText(text + userHash);
        mailSender.send(message);
        user.setHash(userHash);
        user.setHashExpiryTime(LocalDateTime.now().plusMinutes(expiryMinutes));
        userRepository.save(user);
    }

    @Scheduled(fixedDelayString = "${delete-hash.delay-ms}")
    @Transactional
    public void deleteHashWithDelay() {
        userRepository.clearHashAfterExpiry(LocalDateTime.now());
    }

}
