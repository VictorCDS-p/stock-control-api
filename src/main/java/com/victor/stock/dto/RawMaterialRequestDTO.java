package com.victor.stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RawMaterialRequestDTO {

    @NotBlank
    public String code;

    @NotBlank
    public String name;

    @NotNull
    @Min(0)
    public Integer stockQuantity;
}