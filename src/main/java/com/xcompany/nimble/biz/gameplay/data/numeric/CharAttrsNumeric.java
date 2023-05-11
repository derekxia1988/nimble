package com.xcompany.nimble.biz.gameplay.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class CharAttrsNumeric implements NumericTable {
    /**
     * id：
     */
    private int id = 0;
    /**
     * 
     */
    private float attackB = 0f;
    /**
     * 
     */
    private float attackI = 0f;
    /**
     * 
     */
    private float defB = 0f;
    /**
     * 
     */
    private float defI = 0f;
    /**
     * 
     */
    private float hpB = 0f;
    /**
     * 
     */
    private float hpI = 0f;
    /**
     * 
     */
    private float speedB = 0f;
    /**
     * 
     */
    private float speedI = 0f;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }


    public float getAttackB() {
        return attackB;
    }

    public void setAttackB(float newValue) {
        attackB = newValue;
    }


    public float getAttackI() {
        return attackI;
    }

    public void setAttackI(float newValue) {
        attackI = newValue;
    }


    public float getDefB() {
        return defB;
    }

    public void setDefB(float newValue) {
        defB = newValue;
    }


    public float getDefI() {
        return defI;
    }

    public void setDefI(float newValue) {
        defI = newValue;
    }


    public float getHpB() {
        return hpB;
    }

    public void setHpB(float newValue) {
        hpB = newValue;
    }


    public float getHpI() {
        return hpI;
    }

    public void setHpI(float newValue) {
        hpI = newValue;
    }


    public float getSpeedB() {
        return speedB;
    }

    public void setSpeedB(float newValue) {
        speedB = newValue;
    }


    public float getSpeedI() {
        return speedI;
    }

    public void setSpeedI(float newValue) {
        speedI = newValue;
    }


}