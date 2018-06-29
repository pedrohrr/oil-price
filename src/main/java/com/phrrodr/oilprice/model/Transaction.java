package com.phrrodr.oilprice.model;

import com.phrrodr.oilprice.enumeration.TransactionType;
import lombok.Getter;
import org.junit.Assert;

import java.time.LocalDateTime;

@Getter
public class Transaction {

    private LocalDateTime timestamp;
    private Oil oil;
    private Double quantity;
    private TransactionType type;
    private Double price;

    public Transaction(final Oil oil, final Double quantity, final TransactionType type, final Double price) {
        Assert.assertNotNull("Oil cannot be null", oil);
        Assert.assertNotNull("Quantity cannot be null", quantity);
        Assert.assertNotNull("Type cannot be null", type);
        Assert.assertNotNull("Price cannot be null", price);

        this.oil = oil;
        this.quantity = quantity;
        this.type = type;
        this.price = price;
        this.timestamp = LocalDateTime.now();
    }

    public Double quantityOverPrice() {
        return quantity * price;
    }
}