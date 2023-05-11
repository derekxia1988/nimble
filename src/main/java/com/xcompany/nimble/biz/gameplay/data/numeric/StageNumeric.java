package com.xcompany.nimble.biz.gameplay.data.numeric;

import com.xcompany.nimble.biz.numeric.NumericTable;
import java.util.*;


public class StageNumeric implements NumericTable {
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
    private int time = 0;
    /**
     * 
     */
    private int round = 0;
    /**
     * 
     */
    private int[] rewards = new int[]{0};
    /**
     * 
     */
    private int[] enemyList = new int[]{0, 0, 0, 0, 0, 0};
    /**
     * 
     */
    private int[] levelList = new int[]{0, 0, 0, 0, 0, 0};

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


    public int getTime() {
        return time;
    }

    public void setTime(int newValue) {
        time = newValue;
    }


    public int getRound() {
        return round;
    }

    public void setRound(int newValue) {
        round = newValue;
    }


    public int[] getRewards() {
        return rewards;
    }

    public void setRewards(int[] newValue) {
        rewards = newValue;
    }


    public int[] getEnemyList() {
        return enemyList;
    }

    public void setEnemyList(int[] newValue) {
        enemyList = newValue;
    }


    public int[] getLevelList() {
        return levelList;
    }

    public void setLevelList(int[] newValue) {
        levelList = newValue;
    }


}