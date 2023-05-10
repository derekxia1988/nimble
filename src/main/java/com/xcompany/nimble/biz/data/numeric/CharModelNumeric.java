package com.xcompany.nimble.biz.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class CharModelNumeric implements NumericTable {
    /**
     * id：
     */
    private int id = 0;
    /**
     * 
     */
    private String spriteName = "none";
    /**
     * 
     */
    private int spriteScale = 1;

    @Override
    public int getId() {
        return id;
    }

    public void setId(int newValue) {
        id = newValue;
    }


    public String getSpriteName() {
        return spriteName;
    }

    public void setSpriteName(String newValue) {
        spriteName = newValue;
    }


    public int getSpriteScale() {
        return spriteScale;
    }

    public void setSpriteScale(int newValue) {
        spriteScale = newValue;
    }


}