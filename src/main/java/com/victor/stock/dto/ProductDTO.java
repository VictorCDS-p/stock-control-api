package com.victor.stock.dto;

import com.victor.stock.entity.ProductMaterial;
import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {
    public Long id;
    public String code;
    public String name;
    public Double value;
    public List<ProductMaterialDTO> materials;

    public ProductDTO() {}

    public ProductDTO(com.victor.stock.entity.Product product) {
        this.id = product.id;
        this.code = product.code;
        this.name = product.name;
        this.value = product.value;
        this.materials = product.materials != null ?
                product.materials.stream()
                        .map(ProductMaterialDTO::new)
                        .collect(Collectors.toList())
                : List.of();
    }
}