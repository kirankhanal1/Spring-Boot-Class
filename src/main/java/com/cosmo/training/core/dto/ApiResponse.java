package com.cosmo.training.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:s a")
    private LocalDateTime timestamp;


    public ApiResponse(boolean success, String message, Integer status) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
