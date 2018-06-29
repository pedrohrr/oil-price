package com.phrrodr.oilprice.model;

import com.phrrodr.oilprice.enumeration.OilType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Oil {

    @EqualsAndHashCode.Include
    private String id;

    private OilType type;

    private Integer fixedRevenue;

    private Double variableRevenue;

    private Double barrelValue;

    public Oil(String id, OilType type, Integer fixedRevenue, Double barrelValue) {
        this.id = id;
        this.type = type;
        this.fixedRevenue = fixedRevenue;
        this.barrelValue = barrelValue;
    }

    public Oil(String id, OilType type, Integer fixedRevenue, Double variableRevenue, Double barrelValue) {
        this.id = id;
        this.type = type;
        this.fixedRevenue = fixedRevenue;
        this.variableRevenue = variableRevenue;
        this.barrelValue = barrelValue;
    }
}