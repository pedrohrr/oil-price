package com.phrrodr.oilprice.data;

import lombok.Data;

@Data
public class InventoryIndexData {

    private String oil;
    private Double geometricMean;

    public InventoryIndexData(String oil, Double geometricMean) {
        this.oil = oil;
        this.geometricMean = geometricMean;
    }
}
