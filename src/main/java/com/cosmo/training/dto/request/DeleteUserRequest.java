package com.cosmo.training.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteUserRequest {
    @NotNull(message = "Id is required")
    private Integer id;
}
