package com.xcompany.nimble.biz.gameplay.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class CharRankNumeric implements NumericTable {
    /**
     * id：
     */
    private int id = 0;
    /**
     * 
     */
    private int cost = 0;
    /**
     * 
     */
    private int cost1 = 0;
    /**
     * 
     */
    private int levelCap = 0;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int newValue) {
        cost = newValue;
    }


    public int getCost1() {
        return cost1;
    }

    public void setCost1(int newValue) {
        cost1 = newValue;
    }


    public int getLevelCap() {
        return levelCap;
    }

    public void setLevelCap(int newValue) {
        levelCap = newValue;
    }


}