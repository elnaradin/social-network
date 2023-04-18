package ru.itgroup.intouch.mapper;

import model.NotificationSettings;
import model.enums.NotificationType;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import ru.itgroup.intouch.dto.response.settings.SettingsItemDto;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class NotificationSettingsMapper {
    private final List<SettingsItemDto> settings = new ArrayList<>();

    public List<SettingsItemDto> getDestination(NotificationSettings notificationSettings)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        for (NotificationType NotificationType : NotificationType.values()) {
            addSettingItemDto(notificationSettings, NotificationType);
        }

        return settings;
    }

    private void addSettingItemDto(
            NotificationSettings notificationSettings, @NotNull NotificationType NotificationType
    )
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String label = NotificationType.getLabel();
        Method getter = NotificationSettings.class.getMethod("is" + StringUtils.capitalize(label));
        settings.add(SettingsItemDto.builder()
                             .enable((boolean) getter.invoke(notificationSettings))
                             .notificationType(NotificationType.name())
                             .build());
    }
}
