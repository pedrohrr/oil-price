package com.phrrodr.oilprice.service;

import com.phrrodr.oilprice.enumeration.OilType;
import com.phrrodr.oilprice.model.Oil;
import com.phrrodr.oilprice.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;

public class OilCalculationServiceTest {

    private static final double NO_MARGIN = 0.0;

    private OilCalculationService service;

    @Before
    public void setup() {
        service = new OilCalculationService();
    }

    @Test(expected = AssertionError.class)
    public void revenueYieldNullOil() {
        service.revenueYield(null, 10.0);
    }

    @Test(expected = AssertionError.class)
    public void revenueYieldNullPrice() {
        service.revenueYield(Mockito.mock(Oil.class), null);
    }

    @Test(expected = AssertionError.class)
    public void revenueYieldEmptyOil() {
        service.revenueYield(Mockito.mock(Oil.class), 10.0);
    }

    @Test(expected = AssertionError.class)
    public void revenueYieldStandardNullFixedRevenue() {
        final Oil oil = Mockito.mock(Oil.class);
        Mockito.when(oil.getType()).thenReturn(OilType.STANDARD);
        Mockito.when(oil.getFixedRevenue()).thenReturn(null);
        service.revenueYield(oil, 10.0);
    }

    @Test
    public void revenueYieldStandard() {
        final Oil oil = Mockito.mock(Oil.class);
        Mockito.when(oil.getType()).thenReturn(OilType.STANDARD);
        Mockito.when(oil.getFixedRevenue()).thenReturn(10);
        Assert.assertEquals(1.0, service.revenueYield(oil, 10.0), NO_MARGIN);
    }

    @Test(expected = AssertionError.class)
    public void revenueYieldPremiumNullVariableRevenue() {
        final Oil oil = Mockito.mock(Oil.class);
        Mockito.when(oil.getType()).thenReturn(OilType.PREMIUM);
        Mockito.when(oil.getVariableRevenue()).thenReturn(null);
        Mockito.when(oil.getBarrelValue()).thenReturn(10.0);
        Assert.assertEquals(1.0, service.revenueYield(oil, 10.0), NO_MARGIN);
    }

    @Test(expected = AssertionError.class)
    public void revenueYieldPremiumNullBarrelValue() {
        final Oil oil = Mockito.mock(Oil.class);
        Mockito.when(oil.getType()).thenReturn(OilType.PREMIUM);
        Mockito.when(oil.getVariableRevenue()).thenReturn(10.0);
        Mockito.when(oil.getBarrelValue()).thenReturn(null);
        Assert.assertEquals(1.0, service.revenueYield(oil, 10.0), NO_MARGIN);
    }

    @Test
    public void revenueYieldPremium() {
        final Oil oil = Mockito.mock(Oil.class);
        Mockito.when(oil.getType()).thenReturn(OilType.PREMIUM);
        Mockito.when(oil.getVariableRevenue()).thenReturn(10.0);
        Mockito.when(oil.getBarrelValue()).thenReturn(20.0);
        Assert.assertEquals(40.0, service.revenueYield(oil, 5.0), NO_MARGIN);
    }

    @Test(expected = AssertionError.class)
    public void priceEarningsRatioNullRevenue() {
        service.priceEarningsRatio(null, 12.0);
    }

    @Test(expected = AssertionError.class)
    public void priceEarningsRatioNullPrice() {
        service.priceEarningsRatio(10.0, null);
    }

    @Test
    public void priceEarningsRatio() {
        Assert.assertEquals(4.0, service.priceEarningsRatio(3.0, 12.0), NO_MARGIN);
    }

    @Test
    public void volumeWeighted() {
        final Transaction t1 = Mockito.mock(Transaction.class);
        final Transaction t2 = Mockito.mock(Transaction.class);

        Mockito.when(t1.quantityOverPrice()).thenReturn(20.0);
        Mockito.when(t1.getQuantity()).thenReturn(10.0);

        Mockito.when(t2.quantityOverPrice()).thenReturn(30.0);
        Mockito.when(t2.getQuantity()).thenReturn(10.0);

        Assert.assertEquals(2.5, service.volumeWeighted(Arrays.asList(t1, t2)), NO_MARGIN);
    }

    @Test(expected = AssertionError.class)
    public void geometricMeanNullTransactions() {
        service.geometricMean(null);
    }

    @Test
    public void geometricMean() {
        final Transaction t1 = Mockito.mock(Transaction.class);
        final Transaction t2 = Mockito.mock(Transaction.class);
        final Transaction t3 = Mockito.mock(Transaction.class);

        Mockito.when(t1.getPrice()).thenReturn(1.0);
        Mockito.when(t2.getPrice()).thenReturn(2.0);
        Mockito.when(t3.getPrice()).thenReturn(3.0);

        Assert.assertEquals(1.8171207, service.geometricMean(Arrays.asList(t1, t2, t3)), 0.00001);
    }
}