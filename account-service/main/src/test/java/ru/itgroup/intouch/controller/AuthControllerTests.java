package ru.itgroup.intouch.controller;

import jakarta.mail.internet.MimeMessage;
import model.account.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import ru.itgroup.intouch.dto.CaptchaDto;
import ru.itgroup.intouch.dto.EmailDto;
import ru.itgroup.intouch.dto.ImageDTO;
import ru.itgroup.intouch.dto.PasswordDto;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.dto.UploadPhotoDto;
import ru.itgroup.intouch.repository.UserRepository;
import ru.itgroup.intouch.service.account.HashGenerator;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTests extends BaseIT {
    @MockBean
    private HashGenerator hashGenerator;
    @Autowired
    private UserRepository userRepository;

    @Test
    void register() throws Exception {
        when(storageServiceClient.feignUploadPhoto(any(UploadPhotoDto.class)))
                .thenReturn(new ImageDTO("photo name", "photo path"));
        String captchaResponse = mockMvc.perform(get("/api/v1/auth/captcha"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        CaptchaDto captchaDto = objectMapper.readValue(captchaResponse, CaptchaDto.class);

        RegistrationDto registrationDto = RegistrationDto.builder()
                .captchaCode(captchaDto.getSecret())
                .captchaSecret(captchaDto.getSecret())
                .password1("test-pass")
                .password2("test-pass")
                .firstName("Misha")
                .lastName("Petrov")
                .email("email@test.com")
                .build();
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registrationDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void setNewPassword() throws Exception {
        final String hash = UUID.randomUUID().toString();
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        when(hashGenerator.generateHash()).thenReturn(hash);
        when(javaMailSender.createMimeMessage()).thenReturn(mockMimeMessage);
        mockMvc.perform(post("/api/v1/auth/password/recovery/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new EmailDto("lsaltmarsh0@slashdot.org"))))
                .andDo(print())
                .andExpect(status().isOk());
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
        PasswordDto passwordDto = new PasswordDto("test-pass");
        mockMvc.perform(post("/api/v1/auth/password/recovery/{linkId}", hash)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(passwordDto)))
                .andDo(print())
                .andExpect(status().isOk());
        User user = new User();
        user.setEmail("lsaltmarsh0@slashdot.org");
        user = userRepository.findOne(Example.of(user)).orElseThrow();
        assertEquals(user.getPassword(), passwordDto.getPassword());
    }

    @Test
    void captcha() throws Exception {
        when(storageServiceClient.feignUploadPhoto(any(UploadPhotoDto.class)))
                .thenReturn(new ImageDTO("photo name", "photo path"));
        String captchaResponse = mockMvc.perform(get("/api/v1/auth/captcha"))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        CaptchaDto captchaDto = objectMapper.readValue(captchaResponse, CaptchaDto.class);
        assertNotNull(captchaDto.getSecret());
        assertNotNull(captchaDto.getImage());
    }

}