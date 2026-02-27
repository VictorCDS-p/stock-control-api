package com.victor.stock.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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

    @Min(0)
    @Column(nullable = false)
    public Double value;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    public List<ProductMaterial> materials;
}