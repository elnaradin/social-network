package ru.itgroup.intouch.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import ru.itgroup.intouch.dto.request.NotificationRequestDto;

public class CustomDeserializer implements Deserializer<NotificationRequestDto> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public NotificationRequestDto deserialize(String topic, byte[] data) {
        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, NotificationRequestDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize message", e);
        }
    }
}
