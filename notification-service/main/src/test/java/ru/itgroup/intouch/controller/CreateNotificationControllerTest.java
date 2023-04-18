package ru.itgroup.intouch.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.enums.NotificationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;
import ru.itgroup.intouch.service.NotificationCreatorService;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CreateNotificationController.class)
@AutoConfigureMockMvc
class CreateNotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationCreatorService notificationCreatorService;

    @Test
    @DisplayName("Эндпоинт создания уведомления")
    void testCreateNotification() throws Exception {
        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        notificationRequestDto.setAuthorId(1L);
        notificationRequestDto.setReceiverId(2L);
        notificationRequestDto.setNotificationType(NotificationType.POST.name());
        notificationRequestDto.setContent("Hello from test!");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/notifications/add")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(notificationRequestDto)))
                .andDo(print())
                .andExpect(status().isOk());
    }
}

