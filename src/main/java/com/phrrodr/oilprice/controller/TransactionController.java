package com.phrrodr.oilprice.controller;

import com.phrrodr.oilprice.data.InventoryIndexData;
import com.phrrodr.oilprice.data.TransactionData;
import com.phrrodr.oilprice.exception.BadRequestException;
import com.phrrodr.oilprice.exception.EmptyResultException;
import com.phrrodr.oilprice.facade.TransactionFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionFacade facade;

    @PostMapping
    public long register(@RequestBody final TransactionData data) throws BadRequestException {
        try {
            return facade.register(data);
        } catch (AssertionError e) {
            throw new BadRequestException(e);
        }
    }

    @GetMapping("/volume-weighted")
    public double calculateVolumeWeighted() throws EmptyResultException {
        try {
            return facade.calculateVolumeWeighted();
        } catch (AssertionError e) {
            throw new EmptyResultException(e);
        }
    }

    @GetMapping("/inventory-index")
    public List<InventoryIndexData> calculateInventoryIndex() throws EmptyResultException {
        try {
            return facade.calculateInventoryIndex();
        } catch (AssertionError e) {
            throw new EmptyResultException(e);
        }
    }

}