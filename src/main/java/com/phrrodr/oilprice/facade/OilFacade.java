package com.phrrodr.oilprice.facade;

import com.phrrodr.oilprice.model.Oil;
import com.phrrodr.oilprice.repository.OilRepository;
import com.phrrodr.oilprice.service.OilCalculationService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OilFacade {

    @Autowired
    private OilCalculationService calculationService;

    @Autowired
    private OilRepository repository;

    public Double calculateRevenueYield(final String oilId, final Double price) {
        final Oil oil = repository.findById(oilId);
        Assert.assertNotNull("Oil not found for the given id", oil);
        return calculationService.revenueYield(oil, price);
    }

    public Double calculatePriceEarningsRatio(final String oilId, final Double price) {
        final Double revenue = calculateRevenueYield(oilId, price);
        return calculationService.priceEarningsRatio(revenue, price);
    }

}
