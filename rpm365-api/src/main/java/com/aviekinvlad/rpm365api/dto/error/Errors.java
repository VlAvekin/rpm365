package com.aviekinvlad.rpm365api.dto.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Data
@NoArgsConstructor
public class Errors {

    private HttpStatus status;
    private String error;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    public Errors(HttpStatus status, String error) {
        this.status = status;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }
}
