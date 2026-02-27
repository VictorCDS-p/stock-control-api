package com.victor.stock.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "product_material")
public class ProductMaterial extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    public Product product;

    @ManyToOne
    @JoinColumn(name = "raw_material_id", nullable = false)
    public RawMaterial rawMaterial;

    @Min(1)
    @Column(name = "required_quantity", nullable = false)
    public Integer requiredQuantity;
}