package ru.itgroup.intouch.client.exceptionHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import ru.itgroup.intouch.client.exceptionHandling.exceptions.BadRequestException;
import ru.itgroup.intouch.client.exceptionHandling.exceptions.UnauthorizedException;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder errorDecoder = new Default();


    @Override
    public Exception decode(String s, Response response) {
        ErrorResponse message = null;
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ErrorResponse.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }
        log.info(message.toString());
        return switch (response.status()) {
            case 400 -> new BadRequestException(message.getMessage() != null ? message.getMessage() : "Bad Request");
            case 401 -> new UnauthorizedException(message.getMessage() != null ? message.getMessage() : "Unauthorized");
            default -> errorDecoder.decode(s, response);
        };
    }


}
