package ru.itgroup.intouch.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import ru.itgroup.intouch.dto.message.SendMessageDto;

public class CustomDeserializer implements Deserializer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Object deserialize(String s, byte[] data) {

        if (data == null) {
            return null;
        }

        try {
            return objectMapper.readValue(data, SendMessageDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize message in message-service", e);
        }
    }
}

