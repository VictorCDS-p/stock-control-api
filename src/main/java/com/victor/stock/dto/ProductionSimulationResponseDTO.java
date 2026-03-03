package com.victor.stock.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductionSimulationResponseDTO {

    public List<ProductionSimulationItemDTO> items;
    public BigDecimal totalProductionValue;

    public ProductionSimulationResponseDTO(List<ProductionSimulationItemDTO> items,
                                           BigDecimal totalProductionValue) {
        this.items = items;
        this.totalProductionValue = totalProductionValue;
    }
}