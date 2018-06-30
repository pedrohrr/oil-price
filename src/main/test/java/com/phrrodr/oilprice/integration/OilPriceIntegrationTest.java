package com.phrrodr.oilprice.integration;

import com.phrrodr.oilprice.Application;
import com.phrrodr.oilprice.controller.OilCalculationController;
import com.phrrodr.oilprice.controller.TransactionController;
import com.phrrodr.oilprice.data.InventoryIndexData;
import com.phrrodr.oilprice.data.TransactionData;
import com.phrrodr.oilprice.enumeration.TransactionType;
import com.phrrodr.oilprice.exception.BadRequestException;
import com.phrrodr.oilprice.exception.EmptyResultException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class OilPriceIntegrationTest {

    @Autowired
    private OilCalculationController oilCalculationController;

    @Autowired
    private TransactionController transactionController;

    @Before
    public void setup() throws BadRequestException {
        registerTransaction("QFC", 12.3, 23.0);
        registerTransaction("REW", 5.3, 36.0);
        registerTransaction("TIM", 6.8, 10.0);
    }

    @Test
    public void revenueYieldAAC25() throws BadRequestException {
       Assert.assertEquals(0.4, oilCalculationController.revenueYield("AAC", 2.5), 0.0);
    }

    @Test
    public void revenueYieldTIM7() throws BadRequestException {
        Assert.assertEquals(1.11, oilCalculationController.revenueYield("TIM", 7.0), 0.0);
    }

    @Test
    public void priceEarningsRatioBWO() throws BadRequestException {
        Assert.assertEquals(31.1176, oilCalculationController.priceEarningsRatio("BWO", 23.0), 0.0001);
    }

    @Test
    public void calculateVolumeWeighted() throws BadRequestException, EmptyResultException {
        Assert.assertEquals(7.850, transactionController.calculateVolumeWeighted(), 0.001);
    }

    @Test
    public void calculateInventoryIndex() throws EmptyResultException {
        final List<InventoryIndexData> inventory = transactionController.calculateInventoryIndex();
        Assert.assertEquals(6.8, findOil(inventory, "TIM").getGeometricMean(), 0.0001);
        Assert.assertEquals(5.3, findOil(inventory, "REW").getGeometricMean(), 0.0001);
        Assert.assertEquals(12.3, findOil(inventory, "QFC").getGeometricMean(), 0.0001);
    }

    private InventoryIndexData findOil(List<InventoryIndexData> inventory, String oil) {
        return inventory.stream().filter(i -> i.getOil().equals(oil)).findFirst().get();
    }

    private void registerTransaction(String oil, Double price, Double quantity) throws BadRequestException {
        final TransactionData data = new TransactionData();
        data.setOil(oil);
        data.setPrice(price);
        data.setQuantity(quantity);
        data.setType(TransactionType.BUY);
        transactionController.register(data);
    }
}