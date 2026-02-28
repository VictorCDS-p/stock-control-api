package com.victor.stock.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "product")
public class Product extends PanacheEntity {

    @NotBlank
    @Column(unique = true, nullable = false)
    public String code;

    @NotBlank
    @Column(nullable = false)
    public String name;

    @NotNull
    @DecimalMin("0.0")
    @Column(nullable = false)
    public BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    public List<ProductMaterial> materials;
}