package com.mutsa.market.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class NegotiationParameter {
    @NotNull
    private Long itemId;
    @NotNull @NotEmpty
    private Integer suggestedPrice;
    private String status;
    @NotNull @NotEmpty
    private String writer;
    private String password;
}
