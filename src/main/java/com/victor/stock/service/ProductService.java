package com.victor.stock.service;

import com.victor.stock.dto.ProductRequestDTO;
import com.victor.stock.entity.Product;
import com.victor.stock.entity.ProductMaterial;
import com.victor.stock.entity.RawMaterial;
import com.victor.stock.exception.BusinessException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProductService {

    public List<Product> listAll() {
        return Product.listAll();
    }

    public Product findById(Long id) {
        return Product.findById(id);
    }

    public Product findByIdWithMaterials(Long id) {
        return Product.find(
                "SELECT p FROM Product p LEFT JOIN FETCH p.materials WHERE p.id = ?1",
                id
        ).firstResult();
    }

    public Product findByCode(String code) {
        return Product.find("code", code).firstResult();
    }

    @Transactional
    public Product create(ProductRequestDTO dto) {

        if (findByCode(dto.code) != null) {
            throw new BusinessException (
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
}