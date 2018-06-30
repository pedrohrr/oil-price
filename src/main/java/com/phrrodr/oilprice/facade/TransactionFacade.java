package com.phrrodr.oilprice.facade;

import com.phrrodr.oilprice.data.InventoryIndexData;
import com.phrrodr.oilprice.data.TransactionData;
import com.phrrodr.oilprice.model.Oil;
import com.phrrodr.oilprice.model.Transaction;
import com.phrrodr.oilprice.repository.OilRepository;
import com.phrrodr.oilprice.repository.TransactionRepository;
import com.phrrodr.oilprice.service.OilCalculationService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TransactionFacade {

    @Autowired
    private OilRepository oilRepository;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private OilCalculationService calculationService;

    public Long register(final TransactionData data) {
        final Oil oil = oilRepository.findById(data.getOil());
        Assert.assertNotNull("Oil not found for the given id", oil);
        return repository.register(oil, data.getQuantity(), data.getType(), data.getPrice());
    }

    public Double calculateVolumeWeighted() {
        final List<Transaction> transactions = repository.getTransactionsAfter(30L, ChronoUnit.MINUTES);

        if (CollectionUtils.isEmpty(transactions)) {
            Assert.fail("No transactions register in the past 30 minutes");
        }

        return calculationService.volumeWeighted(transactions);
    }

    public List<InventoryIndexData> calculateInventoryIndex() {
        final List<InventoryIndexData> inventory = new ArrayList<>();
        final List<Transaction> transactions = repository.findAll();

        if (CollectionUtils.isEmpty(transactions)) {
            Assert.fail("No transactions register");
        }

        final Set<Oil> oils = transactions.stream().map(Transaction::getOil).collect(Collectors.toSet());
        oils.forEach(o -> inventory.add(calculateOilInventory(transactions, o)));
        return inventory;
    }

    private InventoryIndexData calculateOilInventory(final List<Transaction> transactions, final Oil oil) {
        final List<Transaction> transactionsByOil = transactions.stream().filter(t -> t.getOil().equals(oil)).collect(Collectors.toList());
        return new InventoryIndexData(oil.getId(), calculationService.geometricMean(transactionsByOil));
    }
}