package com.victor.stock.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductRequestDTO {

    @NotBlank
    public String code;

    @NotBlank
    public String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    public BigDecimal price;
}