package com.xcompany.nimble.biz.gameplay.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class CharNumeric implements NumericTable {
    /**
     * id：
     */
    private int id = 0;
    /**
     * 
     */
    private String name = "none";
    /**
     * 
     */
    private String nickName = "none";
    /**
     * 
     */
    private int model = 0;
    /**
     * 
     */
    private int quality = 0;
    /**
     * 
     */
    private int country = 1;
    /**
     * 
     */
    private int noramlSkill = 0;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }


    public String getName() {
        return name;
    }

    public void setName(String newValue) {
        name = newValue;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String newValue) {
        nickName = newValue;
    }


    public int getModel() {
        return model;
    }

    public void setModel(int newValue) {
        model = newValue;
    }


    public int getQuality() {
        return quality;
    }

    public void setQuality(int newValue) {
        quality = newValue;
    }


    public int getCountry() {
        return country;
    }

    public void setCountry(int newValue) {
        country = newValue;
    }


    public int getNoramlSkill() {
        return noramlSkill;
    }

    public void setNoramlSkill(int newValue) {
        noramlSkill = newValue;
    }


}