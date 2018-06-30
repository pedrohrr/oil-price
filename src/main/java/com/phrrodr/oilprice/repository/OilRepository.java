package com.phrrodr.oilprice.repository;

import com.phrrodr.oilprice.enumeration.OilType;
import com.phrrodr.oilprice.model.Oil;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Repository
public class OilRepository {

    private static final List<Oil> OILS = Collections.unmodifiableList(Arrays.asList(
        new Oil("AAC", OilType.STANDARD, 1, 42.0),
        new Oil("REW", OilType.STANDARD, 7, 47.0),
        new Oil("BWO", OilType.STANDARD, 17, 61.0),
        new Oil("TIM", OilType.PREMIUM, 5, 0.07, 111.0),
        new Oil("QFC", OilType.STANDARD, 22, 123.0)
    ));

    public Oil findById(final String id) {
        return OILS.stream().filter(oil -> oil.getId().equals(id)).findFirst().orElse(null);
    }

}