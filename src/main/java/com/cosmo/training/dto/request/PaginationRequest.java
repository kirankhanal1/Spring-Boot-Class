package com.cosmo.training.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationRequest {
    private int page;
    private int size;
    private String keyword;
}
