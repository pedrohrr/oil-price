package com.phrrodr.oilprice.data;

import com.phrrodr.oilprice.enumeration.TransactionType;
import lombok.Data;

@Data
public class TransactionData {

    private Long timestamp;
    private String oil;
    private Double quantity;
    private TransactionType type;
    private Double price;

}