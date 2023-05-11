package com.xcompany.nimble.biz.gameplay.data.numeric;

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
    private int heroCost = 0;
    /**
     * 
     */
    private int loardCost = 0;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }


    public int getHeroCost() {
        return heroCost;
    }

    public void setHeroCost(int newValue) {
        heroCost = newValue;
    }


    public int getLoardCost() {
        return loardCost;
    }

    public void setLoardCost(int newValue) {
        loardCost = newValue;
    }


}