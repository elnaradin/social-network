package ru.itgroup.intouch.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
public class EmailSender {
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @SneakyThrows
    public void send(String to, String subject, String text) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(getMailBody(text), true);
        javaMailSender.send(message);
    }

    private String getMailBody(String text) {
        Context context = new Context();
        context.setVariable("text", text);
        return templateEngine.process("emailTemplate", context);
    }
}
