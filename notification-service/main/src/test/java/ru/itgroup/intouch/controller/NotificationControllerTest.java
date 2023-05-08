package ru.itgroup.intouch.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.enums.NotificationType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.itgroup.intouch.dto.response.CountDto;
import ru.itgroup.intouch.dto.response.notifications.AuthorDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationCountDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationDto;
import ru.itgroup.intouch.dto.response.notifications.NotificationListDto;
import ru.itgroup.intouch.mapper.NotificationListMapper;
import ru.itgroup.intouch.mapper.NotificationMapper;
import ru.itgroup.intouch.mapper.UserMapper;
import ru.itgroup.intouch.service.NotificationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.matchesPattern;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NotificationController.class)
@AutoConfigureMockMvc
class NotificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationListMapper notificationListMapper;

    @MockBean
    private NotificationMapper notificationMapper;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private NotificationService notificationService;

    private final String DATE_REGEX = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}(\\.\\d{5,})?";

    @Test
    @DisplayName("Эндпоинт получения количества новых уведомлений")
    void testGetNotificationsCount() throws Exception {
        NotificationCountDto notificationCountDto = NotificationCountDto.builder()
                .data(CountDto.builder().count(10L).build())
                .build();

        given(notificationService.countNewNotifications()).willReturn(notificationCountDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notifications/count"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.timeStamp", matchesPattern(DATE_REGEX)))
                .andExpect(jsonPath("$.data", notNullValue()))
                .andExpect(jsonPath("$.data.count", is(10)))
                .andReturn();
    }

    @Test
    @DisplayName("Эндпоинт получения списка уведомлений")
    void testGetNotifications() throws Exception {
        List<NotificationDto> notificationDtoList = getNotificationDtoList();
        NotificationListDto notificationListDto = NotificationListDto.builder().data(notificationDtoList).build();

        given(notificationService.getNotifications()).willReturn(notificationListDto);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/notifications"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.timeStamp", matchesPattern(DATE_REGEX)))
                .andReturn();

        JsonNode arrayNode = objectMapper.readTree(result.getResponse().getContentAsString()).get("data");
        for (JsonNode element : arrayNode) {
            assertTrue(element.isObject());

            assertTrue(element.has("id"));
            assertTrue(element.get("id").isNumber());

            assertTrue(element.has("author"));
            assertTrue(element.get("author").isObject());

            assertTrue(element.has("content"));
            assertTrue(element.get("content").isTextual());

            assertTrue(element.has("notificationType"));
            assertTrue(element.get("notificationType").isTextual());

            assertTrue(element.has("sentTime"));
            assertTrue(element.get("sentTime").isTextual());
            assertThat(element.get("sentTime").asText(), matchesPattern(DATE_REGEX));
        }

        verify(notificationService, times(1)).getNotifications();
        verifyNoMoreInteractions(notificationService);
    }

    private @NotNull List<NotificationDto> getNotificationDtoList() {
        AuthorDto authorDto = AuthorDto.builder().id(1L).name("Mr. Test").build();
        NotificationDto notificationDto = NotificationDto.builder()
                .id(1L)
                .author(authorDto)
                .content("Test content")
                .notificationType(NotificationType.POST)
                .sentTime(LocalDateTime.now())
                .build();

        List<NotificationDto> notifications = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            notifications.add(notificationDto);
        }

        return notifications;
    }
}

