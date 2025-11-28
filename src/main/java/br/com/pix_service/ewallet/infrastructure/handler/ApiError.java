package br.com.pix_service.ewallet.infrastructure.handler;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ApiError<T> implements Serializable {


    private List<String> errors = new ArrayList<>();
    private String timestamp;
    private int statusCode;

    public ApiError() {
    }

    public ApiError(List<String> errors) {
        super();
        this.errors = errors;
    }

    public String getTimestamp() {
        var now = LocalDateTime.now();
        var dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        timestamp = dateTimeFormatter.format(now);
        return timestamp;
    }
}
