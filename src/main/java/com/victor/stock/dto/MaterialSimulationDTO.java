package com.victor.stock.dto;

public class MaterialSimulationDTO {

    public Long rawMaterialId;
    public String rawMaterialName;
    public int stockAvailable;
    public int requiredPerUnit;
    public int possibleProduction;

    public MaterialSimulationDTO(Long rawMaterialId,
                                 String rawMaterialName,
                                 int stockAvailable,
                                 int requiredPerUnit,
                                 int possibleProduction) {
        this.rawMaterialId = rawMaterialId;
        this.rawMaterialName = rawMaterialName;
        this.stockAvailable = stockAvailable;
        this.requiredPerUnit = requiredPerUnit;
        this.possibleProduction = possibleProduction;
    }
}