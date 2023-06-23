package ru.itgroup.intouch.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.common.serialization.Serializer;
import ru.itgroup.intouch.dto.response.WsDto;

public class CustomSerializer implements Serializer<WsDto> {
    private final ObjectMapper objectMapper;

    public CustomSerializer() {
        objectMapper =
                new ObjectMapper().registerModule(new JavaTimeModule())
                                  .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Override
    public byte[] serialize(String topic, WsDto wsDto) {
        try {
            return objectMapper.writeValueAsBytes(wsDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize DTO to JSON", e);
        }
    }
}
