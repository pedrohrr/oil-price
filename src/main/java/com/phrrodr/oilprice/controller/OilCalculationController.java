package com.phrrodr.oilprice.controller;

import com.phrrodr.oilprice.exception.BadRequestException;
import com.phrrodr.oilprice.facade.OilFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/calculations")
public class OilCalculationController {

    @Autowired
    private OilFacade oilFacade;

    @GetMapping("/revenue-yield")
    public Double revenueYield(
            @RequestParam final String oil,
            @RequestParam final Double price) throws BadRequestException {
        try {
            return oilFacade.calculateRevenueYield(oil, price);
        } catch (AssertionError e) {
            throw new BadRequestException(e);
        }
    }

    @GetMapping("/price-earnings-ratio")
    public Double priceEarningsRatio(
            @RequestParam final String oil,
            @RequestParam final Double price) throws BadRequestException {
        try {
            return oilFacade.calculatePriceEarningsRatio(oil, price);
        } catch (AssertionError e) {
            throw new BadRequestException(e);
        }
    }

}