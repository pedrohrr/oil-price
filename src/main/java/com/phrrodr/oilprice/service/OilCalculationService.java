package com.phrrodr.oilprice.service;

import com.phrrodr.oilprice.enumeration.OilType;
import com.phrrodr.oilprice.model.Oil;
import com.phrrodr.oilprice.model.Transaction;
import org.junit.Assert;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Service
public class OilCalculationService {

    public double revenueYield(final Oil oil, final Double price) {
        Assert.assertNotNull("Oil object cannot be nul", oil);
        Assert.assertNotNull("Oil type is required", oil.getType());
        Assert.assertNotNull("Price is required", price);

        if (OilType.STANDARD.equals(oil.getType())) {
            Assert.assertNotNull("Fixed revenue is required", oil.getFixedRevenue());
            return oil.getFixedRevenue() / price;
        } else {
            Assert.assertNotNull("Variable revenue is required", oil.getVariableRevenue());
            Assert.assertNotNull("Barrel value is required", oil.getBarrelValue());
            return (oil.getVariableRevenue() * oil.getBarrelValue()) / price;
        }
    }

    public double priceEarningsRatio(final Double revenue, final Double price) {
        Assert.assertNotNull("Revenue is required", revenue);
        Assert.assertNotNull("Price is required", price);

        return price / revenue;
    }

    public double volumeWeighted(final List<Transaction> transactions) {
        final Double sumOfQtysOverPrices = transactions.stream().mapToDouble(Transaction::quantityOverPrice).sum();
        final Double sumOfQtys = transactions.stream().mapToDouble(Transaction::getQuantity).sum();
        return sumOfQtysOverPrices / sumOfQtys;
    }

    public double geometricMean(final List<Transaction> transactions) {
        if (CollectionUtils.isEmpty(transactions)) {
            Assert.fail("Transactions cannot be empty");
        }

        final double productOfPrices = transactions.stream().mapToDouble(Transaction::getPrice).reduce(1.0, (x, y) -> x * y);

        return Math.pow(productOfPrices, 1.0 / transactions.size());
    }

}