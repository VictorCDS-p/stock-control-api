package com.victor.stock.dto;

import java.util.List;

public class SimulationResponseDTO {

    public Long productId;
    public String productName;
    public int maxProduction;

    public List<MaterialSimulationDTO> materials;

    public SimulationResponseDTO(Long productId,
                                 String productName,
                                 int maxProduction,
                                 List<MaterialSimulationDTO> materials) {
        this.productId = productId;
        this.productName = productName;
        this.maxProduction = maxProduction;
        this.materials = materials;
    }
}