package com.xcompany.nimble.biz.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class LevelNumeric implements NumericTable {
    /**
     * id：
     */
    private int id = 0;
    /**
     * 
     */
    private int next = 0;
    /**
     * 
     */
    private int type = 0;
    /**
     * 
     */
    private int[] stage = new int[]{0};
    /**
     * 
     */
    private int reward = 0;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }


    public int getNext() {
        return next;
    }

    public void setNext(int newValue) {
        next = newValue;
    }


    public int getType() {
        return type;
    }

    public void setType(int newValue) {
        type = newValue;
    }


    public int[] getStage() {
        return stage;
    }

    public void setStage(int[] newValue) {
        stage = newValue;
    }


    public int getReward() {
        return reward;
    }

    public void setReward(int newValue) {
        reward = newValue;
    }


}