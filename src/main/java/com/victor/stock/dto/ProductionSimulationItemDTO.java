package com.victor.stock.dto;

import java.math.BigDecimal;

public class ProductionSimulationItemDTO {

    public Long productId;
    public String productName;
    public int quantity;
    public BigDecimal unitPrice;
    public BigDecimal totalValue;

    public ProductionSimulationItemDTO(Long productId,
                                       String productName,
                                       int quantity,
                                       BigDecimal unitPrice,
                                       BigDecimal totalValue) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalValue = totalValue;
    }
}