package com.victor.stock.service;

import com.victor.stock.dto.ProductRequestDTO;
import com.victor.stock.dto.ProductionSimulationItemDTO;
import com.victor.stock.dto.ProductionSimulationResponseDTO;
import com.victor.stock.entity.Product;
import com.victor.stock.entity.ProductMaterial;
import com.victor.stock.entity.RawMaterial;
import com.victor.stock.exception.BusinessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.*;

@ApplicationScoped
public class ProductService {

    public List<Product> listAll() {
        return Product.listAll();
    }

    public Product findByIdWithMaterials(Long id) {
        return Product.find(
                "SELECT DISTINCT p FROM Product p " +
                        "LEFT JOIN FETCH p.materials pm " +
                        "LEFT JOIN FETCH pm.rawMaterial " +
                        "WHERE p.id = ?1",
                id
        ).firstResult();
    }

    public Product findByCode(String code) {
        return Product.find("code", code).firstResult();
    }

    @Transactional
    public Product create(ProductRequestDTO dto) {

        if (findByCode(dto.code) != null) {
            throw new BusinessException(
                    "Product code '" + dto.code + "' already exists"
            );
        }

        Product product = new Product();
        product.code = dto.code;
        product.name = dto.name;
        product.price = dto.price;
        product.persist();

        return product;
    }

    @Transactional
    public Product update(Long id, ProductRequestDTO dto) {

        Product product = findByIdWithMaterials(id);

        if (product == null) {
            return null;
        }

        Product conflict = findByCode(dto.code);

        if (conflict != null && !conflict.id.equals(id)) {
            throw new BusinessException(
                    "Product code '" + dto.code + "' already exists"
            );
        }

        product.code = dto.code;
        product.name = dto.name;
        product.price = dto.price;

        return product;
    }

    @Transactional
    public boolean delete(Long id) {
        return Product.deleteById(id);
    }

    @Transactional
    public ProductMaterial addMaterial(Long productId,
                                       RawMaterial rawMaterial,
                                       int requiredQuantity) {

        Product product = Product.findById(productId);

        if (product == null) {
            return null;
        }

        ProductMaterial pm = new ProductMaterial();
        pm.product = product;
        pm.rawMaterial = rawMaterial;
        pm.requiredQuantity = requiredQuantity;
        pm.persist();

        return pm;
    }

    public ProductionSimulationResponseDTO simulateProduction() {

        List<Product> products = Product.list(
                "SELECT DISTINCT p FROM Product p " +
                        "LEFT JOIN FETCH p.materials pm " +
                        "LEFT JOIN FETCH pm.rawMaterial " +
                        "ORDER BY p.price DESC"
        );

        Map<Long, Integer> simulatedStock = new HashMap<>();

        List<RawMaterial> allMaterials = RawMaterial.listAll();

        for (RawMaterial rm : allMaterials) {
            simulatedStock.put(rm.id, rm.stockQuantity);
        }

        List<ProductionSimulationItemDTO> results = new ArrayList<>();
        BigDecimal totalProductionValue = BigDecimal.ZERO;

        for (Product product : products) {

            if (product.materials == null || product.materials.isEmpty()) {
                continue;
            }

            int maxProduction = product.materials.stream()
                    .mapToInt(pm -> {
                        Integer available = simulatedStock.get(pm.rawMaterial.id);
                        if (available == null || pm.requiredQuantity == 0) {
                            return 0;
                        }
                        return available / pm.requiredQuantity;
                    })
                    .min()
                    .orElse(0);

            if (maxProduction <= 0) {
                continue;
            }

            for (ProductMaterial pm : product.materials) {
                Long materialId = pm.rawMaterial.id;
                int usedQuantity = maxProduction * pm.requiredQuantity;

                simulatedStock.put(
                        materialId,
                        simulatedStock.get(materialId) - usedQuantity
                );
            }

            BigDecimal itemTotal = product.price
                    .multiply(BigDecimal.valueOf(maxProduction));

            totalProductionValue = totalProductionValue.add(itemTotal);

            results.add(
                    new ProductionSimulationItemDTO(
                            product.id,
                            product.name,
                            maxProduction,
                            product.price,
                            itemTotal
                    )
            );
        }

        return new ProductionSimulationResponseDTO(
                results,
                totalProductionValue
        );
    }
}