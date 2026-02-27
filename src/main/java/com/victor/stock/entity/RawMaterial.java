package com.victor.stock.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table(name = "raw_material")
public class RawMaterial extends PanacheEntity {

    @NotBlank
    @Column(unique = true, nullable = false)
    public String code;

    @NotBlank
    @Column(nullable = false)
    public String name;

    @Min(0)
    @Column(name = "stock_quantity", nullable = false)
    public Integer stockQuantity;

    @OneToMany(mappedBy = "rawMaterial", cascade = CascadeType.ALL)
    public List<ProductMaterial> productMaterials;
}