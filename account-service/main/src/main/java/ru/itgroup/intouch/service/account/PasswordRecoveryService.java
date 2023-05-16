package ru.itgroup.intouch.service.account;

import lombok.RequiredArgsConstructor;
import model.account.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.repository.UserRepository;

import java.util.Optional;

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

    private final PasswordEncoder passwordEncoder;

    public void setNewPassword(String linkId, String password) {
        Optional<User> firstByEmail = userRepository.findFirstByHashcode(linkId);
        if (firstByEmail.isEmpty()) {
            throw new UsernameNotFoundException("user not found");
        }
        firstByEmail.get().setPassword(passwordEncoder.encode(password));
        userRepository.save(firstByEmail.get());
    }

    public void sendLetter(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        Optional<User> optUser = userRepository.findFirstByEmail(to);
        if (optUser.isEmpty()) {
            throw new UsernameNotFoundException("no user with email " + to + " found");
        }
        User user = optUser.get();
        String userHashcode = String.valueOf(user.hashCode());
        message.setText(text + userHashcode);
        mailSender.send(message);
        user.setHashcode(userHashcode);
        userRepository.save(user);
        deleteHashWithDelay(userRepository.save(user));
    }

    void deleteHashWithDelay(User user) {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(900000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            user.setHashcode(null);
            userRepository.save(user);
        });
        thread.start();
    }
}
