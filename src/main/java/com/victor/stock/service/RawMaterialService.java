package com.victor.stock.service;

import com.victor.stock.dto.RawMaterialRequestDTO;
import com.victor.stock.entity.RawMaterial;
import com.victor.stock.exception.BusinessException;
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

    public RawMaterial findByCode(String code) {
        return RawMaterial.find("code", code).firstResult();
    }

    @Transactional
    public RawMaterial create(RawMaterialRequestDTO dto) {

        if (findByCode(dto.code) != null) {
            throw new BusinessException (
                    "Raw material code '" + dto.code + "' already exists"
            );
        }

        RawMaterial material = new RawMaterial();
        material.code = dto.code;
        material.name = dto.name;
        material.stockQuantity = dto.stockQuantity;
        material.persist();

        return material;
    }

    @Transactional
    public RawMaterial update(Long id, RawMaterialRequestDTO dto) {

        RawMaterial material = findById(id);
        if (material == null) {
            return null;
        }

        RawMaterial conflict = findByCode(dto.code);
        if (conflict != null && !conflict.id.equals(id)) {
            throw new BusinessException(
                    "Raw material code '" + dto.code + "' already exists"
            );
        }

        material.code = dto.code;
        material.name = dto.name;
        material.stockQuantity = dto.stockQuantity;

        return material;
    }

    @Transactional
    public boolean delete(Long id) {
        return RawMaterial.deleteById(id);
    }
}