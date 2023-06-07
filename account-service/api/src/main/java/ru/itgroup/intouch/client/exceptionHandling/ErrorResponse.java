package ru.itgroup.intouch.client.exceptionHandling;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private Date timestamp;

    @JsonProperty(value = "code")
    private int code;

    @JsonProperty(value = "status")
    private String status;

    @JsonProperty(value = "error_description")
    private String message;

    @JsonProperty(value = "details")
    private String details;

    public ErrorResponse(int code, String status, String message, String details) {
        this.timestamp = new Date();
        this.code = code;
        this.status = status;
        this.message = message;
        this.details = details;
    }

    public ErrorResponse() {

    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "timestamp=" + timestamp +
                ", code=" + code +
                ", status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
