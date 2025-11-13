package com.cosmo.training.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ListUserResponse {
    private String username;
    private String fullName;
    private String email;
    private Boolean isDeleted;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:s a")
    private LocalDateTime createdAt;
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:s a")
    private LocalDateTime updatedAt;
}
