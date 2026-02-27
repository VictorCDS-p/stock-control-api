package com.victor.stock.service;

import com.victor.stock.entity.Product;
import com.victor.stock.entity.ProductMaterial;
import com.victor.stock.entity.RawMaterial;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ProductService {

    public List<Product> listAll() {
        return Product.listAll();
    }

    public Product findByIdWithMaterials(Long id) {
        return Product.find(
                "SELECT p FROM Product p LEFT JOIN FETCH p.materials WHERE p.id = ?1", id
        ).firstResult();
    }

    public Product findById(Long id) {
        return Product.findById(id);
    }

    public Product findByCode(String code) {
        return Product.find("code", code).firstResult();
    }

    @Transactional
    public Product create(Product product) {
        product.persist();
        return product;
    }

    @Transactional
    public Product update(Long id, Product updatedProduct) {
        Product product = Product.findById(id);
        if (product == null) {
            return null;
        }

        Product conflict = findByCode(updatedProduct.code);
        if (conflict != null && !conflict.id.equals(id)) {
            throw new RuntimeException("Product code '" + updatedProduct.code + "' already exists");
        }

        product.code = updatedProduct.code;
        product.name = updatedProduct.name;
        product.value = updatedProduct.value;

        return findByIdWithMaterials(id);
    }

    @Transactional
    public boolean delete(Long id) {
        return Product.deleteById(id);
    }

    @Transactional
    public ProductMaterial addMaterial(Long productId, RawMaterial rawMaterial, int requiredQuantity) {
        Product product = Product.findById(productId);
        if (product == null || rawMaterial == null) {
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