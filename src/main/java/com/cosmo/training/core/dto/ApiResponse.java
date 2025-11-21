package com.cosmo.training.core.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ApiResponse<T> implements Serializable {
    private boolean success;
    private String message;
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:s a")
    private LocalDateTime timestamp;
    private T data;


    public ApiResponse(boolean success, String message, Integer status) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public ApiResponse(boolean success, String message, Integer status, T data) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

}
