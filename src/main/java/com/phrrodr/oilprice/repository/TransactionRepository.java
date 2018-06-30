package com.phrrodr.oilprice.repository;

import com.phrrodr.oilprice.enumeration.TransactionType;
import com.phrrodr.oilprice.model.Oil;
import com.phrrodr.oilprice.model.Transaction;
import org.junit.Assert;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    private static final List<Transaction> TRANSACTIONS = new ArrayList<>();
    private static final Map<Oil, Double> INVENTORY = new HashMap<>();

    public synchronized Long register(final Oil oil, final Double quantity, final TransactionType type, final Double price) {
        final Transaction transaction = new Transaction(oil, quantity, type, price);

        if (TransactionType.SELL.equals(type) && !hasQuantityInStock(oil)) {
            Assert.fail("No quantity in stock");
        } else if (TransactionType.BUY.equals(type)) {
            increaseStock(oil, quantity);
        }

        TRANSACTIONS.add(transaction);
        return Timestamp.valueOf(transaction.getTimestamp()).getTime();
    }

    private void increaseStock(final Oil oil, final Double quantity) {
        INVENTORY.merge(oil, quantity, (a, b) -> a + b);
    }

    private boolean hasQuantityInStock(final Oil oil) {
        return getOilQuantityInStock(oil) > 0;
    }

    private Double getOilQuantityInStock(final Oil oil) {
        final Double quantity = INVENTORY.get(oil);
        return quantity == null? 0.0 : quantity;
    }

    public List<Transaction> getTransactionsAfter(final Long amount, final ChronoUnit unit) {
        final  LocalDateTime interval = LocalDateTime.now().minus(amount, unit);
        return TRANSACTIONS.stream().filter(t -> t.getTimestamp().isAfter(interval)).collect(Collectors.toList());
    }

    public List<Transaction> findAll() {
        return TRANSACTIONS;
    }
}