package com.victor.stock.dto;

import com.victor.stock.entity.ProductMaterial;

public class ProductMaterialDTO {
    public Long id;
    public Long rawMaterialId;
    public String rawMaterialCode;
    public String rawMaterialName;
    public Integer requiredQuantity;

    public ProductMaterialDTO() {}

    public ProductMaterialDTO(ProductMaterial pm) {
        this.id = pm.id;
        this.rawMaterialId = pm.rawMaterial.id;
        this.rawMaterialCode = pm.rawMaterial.code;
        this.rawMaterialName = pm.rawMaterial.name;
        this.requiredQuantity = pm.requiredQuantity;
    }
}

