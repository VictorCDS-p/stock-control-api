package com.victor.stock.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class RawMaterialRequestDTO {

    @NotBlank
    public String code;

    @NotBlank
    public String name;

    @Min(0)
    public Integer stockQuantity;
}