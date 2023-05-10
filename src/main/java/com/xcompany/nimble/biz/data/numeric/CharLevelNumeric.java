package com.xcompany.nimble.biz.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class CharLevelNumeric implements NumericTable {
    /**
     * id：
     */
    private int id = 0;
    /**
     * 
     */
    private int coinCost = 0;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }


    public int getCoinCost() {
        return coinCost;
    }

    public void setCoinCost(int newValue) {
        coinCost = newValue;
    }


}