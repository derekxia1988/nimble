package com.xcompany.nimble.biz.gameplay.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class SkillNumeric implements NumericTable {
    /**
     * id：
     */
    private int id = 0;
    /**
     * 
     */
    private int type = 1;
    /**
     * 
     */
    private int scope = 0;
    /**
     * 
     */
    private int target = 0;
    /**
     * 
     */
    private int[] effects = new int[]{1};

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


    public int getScope() {
        return scope;
    }

    public void setScope(int newValue) {
        scope = newValue;
    }


    public int getTarget() {
        return target;
    }

    public void setTarget(int newValue) {
        target = newValue;
    }


    public int[] getEffects() {
        return effects;
    }

    public void setEffects(int[] newValue) {
        effects = newValue;
    }


}