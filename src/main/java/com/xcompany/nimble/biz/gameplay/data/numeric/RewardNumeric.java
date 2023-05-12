package com.xcompany.nimble.biz.gameplay.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class RewardNumeric implements NumericTable {
    /**
     * id：
     */
    private int id = 0;
    /**
     * 
     */
    private int type = 0;
    /**
     * 
     */
    private int itemId = 0;
    /**
     * 
     */
    private int count = 0;
    /**
     * 
     */
    private float[] factors = new float[]{0};

    @Override
    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }


    public int getType() {
        return type;
    }

    public void setType(int newValue) {
        type = newValue;
    }


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int newValue) {
        itemId = newValue;
    }


    public int getCount() {
        return count;
    }

    public void setCount(int newValue) {
        count = newValue;
    }


    public float[] getFactors() {
        return factors;
    }

    public void setFactors(float[] newValue) {
        factors = newValue;
    }


}