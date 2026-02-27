package com.victor.stock.service;

import com.victor.stock.entity.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class RawMaterialService {

    public List<RawMaterial> listAll() {
        return RawMaterial.listAll();
    }

    public RawMaterial findById(Long id) {
        return RawMaterial.findById(id);
    }

    @Transactional
    public RawMaterial create(RawMaterial rawMaterial) {
        rawMaterial.persist();
        return rawMaterial;
    }

    @Transactional
    public RawMaterial update(Long id, RawMaterial updatedRawMaterial) {
        RawMaterial material = RawMaterial.findById(id);
        if (material == null) {
            return null;
        }
        material.code = updatedRawMaterial.code;
        material.name = updatedRawMaterial.name;
        material.stockQuantity = updatedRawMaterial.stockQuantity;
        return material;
    }

    @Transactional
    public boolean delete(Long id) {
        return RawMaterial.deleteById(id);
    }
}