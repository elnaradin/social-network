package ru.itgroup.intouch.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.enums.NotificationType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.itgroup.intouch.dto.request.NotificationSettingsDto;
import ru.itgroup.intouch.dto.response.settings.SettingsItemDto;
import ru.itgroup.intouch.service.NotificationSettingsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = NotificationSettingsController.class)
@AutoConfigureMockMvc
class NotificationSettingsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificationSettingsService notificationSettingsService;

    @Value("${server.api.prefix}")
    private String apiPrefix;

    @Test
    @DisplayName("Эндпоинт получения настроек уведомлений")
    void getSettings() throws Exception {
        List<SettingsItemDto> settings = new ArrayList<>();
        for (NotificationType NotificationType : NotificationType.values()) {
            addSettingItemDto(NotificationType, settings);
        }

        given(notificationSettingsService.getSettings(1L)).willReturn(settings);
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(apiPrefix + "/notifications/settings"))
                                  .andDo(print())
                                  .andExpect(status().isOk())
                                  .andExpect(content().contentType("application/json"))
                                  .andReturn();

        JsonNode arrayNode = objectMapper.readTree(result.getResponse().getContentAsString());
        for (JsonNode element : arrayNode) {
            assertTrue(element.isObject());
            assertTrue(element.has("enable"));
            assertTrue(element.get("enable").isBoolean());
            assertTrue(element.has("notification_type"));
            assertTrue(element.get("notification_type").isTextual());
        }
    }

    @Test
    @DisplayName("Эндпоинт изменения настроек уведомлений")
    void updateSettings() throws Exception {
        NotificationSettingsDto notificationSettingsDto = new NotificationSettingsDto();
        notificationSettingsDto.setNotificationType(String.valueOf(NotificationType.POST));
        notificationSettingsDto.setEnable(false);

        mockMvc.perform(MockMvcRequestBuilders.put(apiPrefix + "/notifications/settings")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(objectMapper.writeValueAsString(notificationSettingsDto)))
               .andDo(print())
               .andExpect(status().isOk());
    }

    private void addSettingItemDto(@NotNull NotificationType typeEnum, @NotNull List<SettingsItemDto> settings) {
        settings.add(SettingsItemDto.builder().enable(true).notificationType(typeEnum.name()).build());
    }
}
