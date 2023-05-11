package com.xcompany.nimble.biz.gameplay.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class EffectNumeric implements NumericTable {
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
    private float[] factor1 = new float[]{0};

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


    public float[] getFactor1() {
        return factor1;
    }

    public void setFactor1(float[] newValue) {
        factor1 = newValue;
    }


}