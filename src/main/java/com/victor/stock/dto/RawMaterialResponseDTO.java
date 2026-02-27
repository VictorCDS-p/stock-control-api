package com.victor.stock.dto;

import com.victor.stock.entity.RawMaterial;

public class RawMaterialResponseDTO {
    public Long id;
    public String code;
    public String name;
    public Integer stockQuantity;

    public RawMaterialResponseDTO(RawMaterial rm) {
        this.id = rm.id;
        this.code = rm.code;
        this.name = rm.name;
        this.stockQuantity = rm.stockQuantity;
    }
}
